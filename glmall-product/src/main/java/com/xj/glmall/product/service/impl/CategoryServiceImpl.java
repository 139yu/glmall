package com.xj.glmall.product.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.xj.glmall.product.service.CategoryBrandRelationService;
import com.xj.glmall.product.vo.Catalog2Vo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.common.utils.Query;

import com.xj.glmall.product.dao.CategoryDao;
import com.xj.glmall.product.entity.CategoryEntity;
import com.xj.glmall.product.service.CategoryService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


@Service("categoryService")
@Transactional
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private CategoryBrandRelationService categoryBrandRelationService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<CategoryEntity> listWithTree() {
        //1、查出所有分类
        List<CategoryEntity> entities = baseMapper.selectList(null);

        //2、组装成父子的树形结构

        //2.1）、找到所有的一级分类
        List<CategoryEntity> level1Menus = entities.stream().filter(categoryEntity ->
                categoryEntity.getParentCid() == 0
        ).map((menu)->{
            menu.setChildren(getChildren(menu,entities));
            if ("44412".equals(menu.getName())) {
                System.out.println(menu.getChildren());
            }
            return menu;
        }).sorted((menu1,menu2)->{
            return (menu1.getSort()==null?0:menu1.getSort()) - (menu2.getSort()==null?0:menu2.getSort());
        }).collect(Collectors.toList());




        return level1Menus;
    }

    @Override
    public void removeMenuByIds(List<Long> asList) {
        //TODO  1、检查当前删除的菜单，是否被别的地方引用

        categoryDao.deleteBatchIds(asList);
    }

    @Override
    public Long[] findCatelogPath(long catelogId) {
        List<Long> path = new ArrayList<>();
        List<Long> parentPath = findParentPath(catelogId,path);
        Collections.reverse(parentPath);
        Long[] parentPathArr = parentPath.toArray(new Long[parentPath.size()]);
        return parentPathArr;
    }

    @Override
    public void updateCascade(CategoryEntity category) {
        this.updateById(category);
        if (!StringUtils.isEmpty(category.getName())) {
            categoryBrandRelationService.updateCategory(category.getCatId(),category.getName());
        }
    }

    @Override
    public List<CategoryEntity> getLevel1Category() {
        List<CategoryEntity> categoryEntities = baseMapper.selectList(new QueryWrapper<CategoryEntity>().eq("parent_cid", 0));
        return categoryEntities;
    }

    @Override
    public Map<String, List<Catalog2Vo>> getCatalogMap() {
       /* //查出所有一级分类
        List<CategoryEntity> level1Category = getLevel1Category();
        //封装数据
        Map<String, List<Catalog2Vo>> collect = level1Category.stream().collect(Collectors.toMap(k -> {
            return k.getCatId().toString();
        }, v -> {
            List<CategoryEntity> catalog2List = baseMapper.selectList(new QueryWrapper<CategoryEntity>().eq("parent_cid", v.getCatId()));
            List<Catalog2Vo> catalog2VoList = null;
            if (catalog2List != null && catalog2List.size() > 0) {
                catalog2VoList = catalog2List.stream().map(level2 -> {
                    Catalog2Vo catalog2Vo = new Catalog2Vo(v.getCatId().toString(), level2.getCatId().toString(), level2.getName(), null);
                    List<CategoryEntity> catalog3List = baseMapper.selectList(new QueryWrapper<CategoryEntity>().eq("parent_cid", level2.getCatId()));
                    List<Catalog2Vo.Catalog3Vo> catalog3VoList = null;
                    if (catalog3List != null && catalog3List.size() > 0) {
                        catalog3VoList = catalog3List.stream().map(level3 -> {
                            Catalog2Vo.Catalog3Vo catalog3Vo = new Catalog2Vo.Catalog3Vo(level2.getCatId().toString(), level3.getCatId().toString(), level3.getName());
                            return catalog3Vo;
                        }).collect(Collectors.toList());
                    }
                    catalog2Vo.setCatalog3List(catalog3VoList);
                    return catalog2Vo;
                }).collect(Collectors.toList());
            }
            return catalog2VoList;
        }));*/
       synchronized (this) {
           /***
            * 优化:将数据库的多次查询改为一次
            */
           //再次确认缓存中是否有数据
           String catalogJson = redisTemplate.opsForValue().get("catalogJson");
           if (!StringUtils.isEmpty(catalogJson)){
               Map<String, List<Catalog2Vo>> map = JSON.parseObject(catalogJson, new TypeReference<Map<String, List<Catalog2Vo>>>() {
               });
               return map;
           }
           return getCatalogJsonFromDB();
       }
    }

    private Map<String, List<Catalog2Vo>> getCatalogJsonFromDB() {
        System.out.println("从数据库查询");
        //查出所有分类
        List<CategoryEntity> categoryList = baseMapper.selectList(new QueryWrapper<CategoryEntity>());
        //获取一级分类
        List<CategoryEntity> level1Category = getCidByParentCid(categoryList, 0L);
        Map<String, List<Catalog2Vo>> collect = level1Category.stream().collect(Collectors.toMap(k -> {
            return k.getCatId().toString();
        }, v -> {
            List<CategoryEntity> level2Category = getCidByParentCid(categoryList, v.getCatId());
            List<Catalog2Vo> catalog2VoList = level2Category.stream().map(item -> {
                Catalog2Vo catalog2Vo = new Catalog2Vo();
                catalog2Vo.setId(item.getCatId().toString());
                catalog2Vo.setName(item.getName());
                catalog2Vo.setCatalog1Id(v.getCatId().toString());
                List<CategoryEntity> level3Category = getCidByParentCid(categoryList, item.getCatId());
                List<Catalog2Vo.Catalog3Vo> catalog3VoList = level3Category.stream().map(level3 -> {
                    Catalog2Vo.Catalog3Vo catalog3Vo = new Catalog2Vo.Catalog3Vo();
                    catalog3Vo.setCatalog2Id(item.getCatId().toString());
                    catalog3Vo.setId(level3.getCatId().toString());
                    catalog3Vo.setName(level3.getName());
                    return catalog3Vo;
                }).collect(Collectors.toList());
                catalog2Vo.setCatalog3List(catalog3VoList);
                return catalog2Vo;
            }).collect(Collectors.toList());
            return catalog2VoList;
        }));
        String toJSONString = JSON.toJSONString(collect);
        redisTemplate.opsForValue().set("catalogJson",toJSONString,1L, TimeUnit.DAYS);
        return collect;
    }

    @Override
    public Map<String, List<Catalog2Vo>> getCatalogJson() {
        String product_lock = UUID.randomUUID().toString();
        //Boolean isLock = redisTemplate.opsForValue().setIfAbsent("product_lock", product_lock, 30, TimeUnit.SECONDS);
        String catalogJson = redisTemplate.opsForValue().get("catalogJson");
        if (StringUtils.isEmpty(catalogJson)) {
            Map<String, List<Catalog2Vo>> catalogMap = getCatalogMap();
            return catalogMap;
        }

        Map<String, List<Catalog2Vo>> map = JSON.parseObject(catalogJson, new TypeReference<Map<String, List<Catalog2Vo>>>() {
        });
        return map;
    }

    private List<CategoryEntity> getCidByParentCid(List<CategoryEntity> categoryList,Long cid){
        List<CategoryEntity> collect = categoryList.stream().filter(item -> item.getParentCid() == cid).collect(Collectors.toList());
        return collect;
    }

    public List<Long> findParentPath(long catelogId,List<Long> path) {
        path.add(catelogId);
        CategoryEntity category = categoryDao.selectById(catelogId);
        if (category.getParentCid() != 0) {
            findParentPath(category.getParentCid(),path);
        }
        return path;
    }

    public List<CategoryEntity> getChildren(CategoryEntity root,List<CategoryEntity> all){
        List<CategoryEntity> children = all.stream().filter(categoryEntity -> {
            if ((categoryEntity.getParentCid().equals(root.getCatId())) && (root.getCatId() == 1441L)){
                System.out.println(categoryEntity);
                System.out.println(root);
            }
            return categoryEntity.getParentCid().equals(root.getCatId());
        }).map(categoryEntity -> {
            //1、找到子菜单
            if(categoryEntity.getName().equals("44412")) {
                System.out.println(categoryEntity);
            }
            categoryEntity.setChildren(getChildren(categoryEntity,all));
            return categoryEntity;
        }).sorted((menu1,menu2)->{
            //2、菜单的排序
            return (menu1.getSort()==null?0:menu1.getSort()) - (menu2.getSort()==null?0:menu2.getSort());
        }).collect(Collectors.toList());

        return children;
    }
}
