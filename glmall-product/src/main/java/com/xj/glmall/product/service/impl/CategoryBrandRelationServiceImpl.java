package com.xj.glmall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.xj.glmall.product.dao.BrandDao;
import com.xj.glmall.product.dao.CategoryDao;
import com.xj.glmall.product.entity.BrandEntity;
import com.xj.glmall.product.entity.CategoryEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.common.utils.Query;

import com.xj.glmall.product.dao.CategoryBrandRelationDao;
import com.xj.glmall.product.entity.CategoryBrandRelationEntity;
import com.xj.glmall.product.service.CategoryBrandRelationService;


@Service("categoryBrandRelationService")
public class CategoryBrandRelationServiceImpl extends ServiceImpl<CategoryBrandRelationDao, CategoryBrandRelationEntity> implements CategoryBrandRelationService {

    @Autowired
    private CategoryBrandRelationDao categoryBrandRelationDao;

    @Autowired
    private BrandDao brandDao;

    @Autowired
    private CategoryDao categoryDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryBrandRelationEntity> page = this.page(
                new Query<CategoryBrandRelationEntity>().getPage(params),
                new QueryWrapper<CategoryBrandRelationEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<CategoryBrandRelationEntity> getRelationList(Long brandId) {
//        IPage<CategoryBrandRelationEntity> page = this.page(new Query<CategoryBrandRelationEntity>().getPage(new HashMap<String, Object>()), new QueryWrapper<CategoryBrandRelationEntity>().eq("brand_id", brandId));
//        return new PageUtils(page);
        return categoryBrandRelationDao.selectList(new QueryWrapper<CategoryBrandRelationEntity>().eq("brand_id",brandId));
    }

    @Override
    public void saveDetails(CategoryBrandRelationEntity categoryBrandRelation) {
        Long brandId = categoryBrandRelation.getBrandId();
        BrandEntity brandEntity = brandDao.selectById(brandId);
        categoryBrandRelation.setBrandName(brandEntity.getName());
        Long catelogId = categoryBrandRelation.getCatelogId();
        CategoryEntity categoryEntity = categoryDao.selectById(catelogId);
        categoryBrandRelation.setCatelogName(categoryEntity.getName());
        Integer count = categoryBrandRelationDao.selectCount(new QueryWrapper<CategoryBrandRelationEntity>().eq("brand_id", brandId).eq("catelog_id", catelogId));
        if(count > 0) {
            return;
        }
        categoryBrandRelationDao.insert(categoryBrandRelation);
    }

    @Override
    public void updateBrand(Long brandId, String name) {
        CategoryBrandRelationEntity categoryBrandRelationEntity = new CategoryBrandRelationEntity();
        categoryBrandRelationEntity.setBrandName(name);
        categoryBrandRelationEntity.setBrandId(brandId);
        categoryBrandRelationDao.update(categoryBrandRelationEntity,new UpdateWrapper<CategoryBrandRelationEntity>().eq("brand_id",brandId));

    }

    @Override
    public void updateCategory(Long catId, String name) {
        this.baseMapper.updateCategory(catId,name);
    }

    @Override
    public List<BrandEntity> getBrandByCatId(Long catId) {
        List<CategoryBrandRelationEntity> relationList = categoryBrandRelationDao.selectList(new QueryWrapper<CategoryBrandRelationEntity>().eq("catelog_id", catId));
        List<BrandEntity> brandList = relationList.stream().map(item -> {
            return brandDao.selectById(item.getBrandId());
        }).collect(Collectors.toList());
        return brandList;
    }


}
