package com.xj.glmall.product.service.impl;

import com.xj.glmall.product.dao.AttrDao;
import com.xj.glmall.product.entity.AttrEntity;
import com.xj.glmall.product.vo.BaseAttrs;
import org.springframework.beans.BeanUtils;
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

import com.xj.glmall.product.dao.ProductAttrValueDao;
import com.xj.glmall.product.entity.ProductAttrValueEntity;
import com.xj.glmall.product.service.ProductAttrValueService;


@Service("productAttrValueService")
public class ProductAttrValueServiceImpl extends ServiceImpl<ProductAttrValueDao, ProductAttrValueEntity> implements ProductAttrValueService {
    @Autowired
    private AttrDao attrDao;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ProductAttrValueEntity> page = this.page(
                new Query<ProductAttrValueEntity>().getPage(params),
                new QueryWrapper<ProductAttrValueEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveAttrs(Long spuId, List<BaseAttrs> baseAttrs) {
        if (baseAttrs != null && baseAttrs.size() > 0) {
            List<ProductAttrValueEntity> collect = baseAttrs.stream().map(item -> {
                ProductAttrValueEntity productAttrValueEntity = new ProductAttrValueEntity();
                productAttrValueEntity.setQuickShow(item.getShowDesc());
                productAttrValueEntity.setAttrId(item.getAttrId());
                AttrEntity attrEntity = attrDao.selectById(item.getAttrId());
                productAttrValueEntity.setAttrName(attrEntity.getAttrName());
                productAttrValueEntity.setAttrValue(item.getAttrValues());
                productAttrValueEntity.setSpuId(spuId);
                return productAttrValueEntity;
            }).collect(Collectors.toList());
            this.saveBatch(collect);
        }
    }

    @Override
    public List<ProductAttrValueEntity> baseAttrListForSpuId(Long spuId) {
        QueryWrapper<ProductAttrValueEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("spu_id",spuId);
        List<ProductAttrValueEntity> productAttrValueEntities = this.baseMapper.selectList(queryWrapper);
        return productAttrValueEntities;
    }

    @Override
    public void updateProductAttrValueBySpuId(Long spuId, List<ProductAttrValueEntity> productAttrValueEntities) {
        this.baseMapper.delete(new QueryWrapper<ProductAttrValueEntity>().eq("spu_id",spuId));
        List<ProductAttrValueEntity> collect = productAttrValueEntities.stream().map(item -> {
            item.setSpuId(spuId);
            return item;
        }).collect(Collectors.toList());
        this.saveBatch(collect);
    }

}
