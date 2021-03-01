package com.xj.glmall.product.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.xj.glmall.product.service.CategoryBrandRelationService;
import com.xj.glmall.product.vo.Catalog2Vo;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
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
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private RedissonClient redisson;

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
        ).map((menu) -> {
            menu.setChildren(getChildren(menu, entities));
            if ("44412".equals(menu.getName())) {
                System.out.println(menu.getChildren());
            }
            return menu;
        }).sorted((menu1, menu2) -> {
            return (menu1.getSort() == null ? 0 : menu1.getSort()) - (menu2.getSort() == null ? 0 : menu2.getSort());
        }).collect(Collectors.toList());


        return level1Menus;
    }

    @Override
    public void removeMenuByIds(List<Long> asList) {
        //TODO  1、检查当前删除的菜单，是否被别的地方引用

        categoryDao.deleteBatchIds(asList);
    }

    @Override
    public Long[] findCatalogPath(long catalogId) {
        List<Long> path = new ArrayList<>();
        List<Long> parentPath = findParentPath(catalogId, path);
        Collections.reverse(parentPath);
        Long[] parentPathArr = parentPath.toArray(new Long[parentPath.size()]);
        return parentPathArr;
    }

    @CacheEvict(value="category",allEntries = true)
    @Override
    public void updateCascade(CategoryEntity category) {
        this.updateById(category);
        if (!StringUtils.isEmpty(category.getName())) {
            categoryBrandRelationService.updateCategory(category.getCatId(), category.getName());
        }
    }
    @Cacheable(value = {"category"},key = "#root.method.name")
    @Override
    public List<CategoryEntity> getLevel1Category() {
        System.out.println(Thread.currentThread().getName());
        List<CategoryEntity> categoryEntities = baseMapper.selectList(new QueryWrapper<CategoryEntity>().eq("parent_cid", 0));
        return categoryEntities;
    }

    /**
     * 使用缓存注解
     * @Cacheable sync = true查询缓存时调用加锁的方法查询
     * @return
     */
    @Cacheable(value = "category",key = "#root.methodName",sync = true)
    @Override
    public Map<String, List<Catalog2Vo>> getCatalogJson() {
        System.out.println(11111);
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
        return collect;
    }

    /**
     * 不使用缓存注解
     * @return
     */
    public Map<String, List<Catalog2Vo>> getCatalogJson2() {
        return getCatalogMapResisson();
    }

    /**
     * 使用redisson实现分布式锁
     * @return
     */
    public Map<String, List<Catalog2Vo>> getCatalogMapResisson(){
        RLock lock = redisson.getLock("productLock");
        lock.lock(30,TimeUnit.SECONDS);
        try{
            Map<String, List<Catalog2Vo>> catalogJsonFromDB = getCatalogJsonFromDB();
            return catalogJsonFromDB;
        }finally {
            lock.unlock();
        }
    }

    /**
     * 分布式锁
     * @return
     */
    public Map<String, List<Catalog2Vo>> getCatalogMapRedisLock() {
        //是每个线程保存在redis中的值都不一样
        String productLock = UUID.randomUUID().toString();
        /**
         * set key value [EX seconds] [PX milliseconds] [NX|XX]
         *  -EX seconds – 设置键key的过期时间，单位时秒
         *  -PX milliseconds – 设置键key的过期时间，单位时毫秒
         *  -NX – 只有键key不存在的时候才会设置key的值，且只有一个线程能设置
         *  -XX – 只有键key存在的时候才会设置key的值，且只有一个线程能设置
         * 一定要让保存锁和设置锁过期时间是一个原子操作，上面的set key value NX命令对应redisTemplate.opsForValue().setIfAbsent（）
         */
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        Boolean isLock = ops.setIfAbsent("productLock", productLock, 30, TimeUnit.SECONDS);
        if (isLock) {
            System.out.println("get lock success");
            Map<String, List<Catalog2Vo>> catalogJsonFromDB = null;
            try{
                catalogJsonFromDB = getCatalogJsonFromDB();
            }finally {
                //判断当前线程的uuid值是否等于redis中锁的值，相等则删除，这是一个原子操作
                String script = "if redis.call(\"get\",KEYS[1]) == ARGV[1]\n" +
                        "then\n" +
                        "    return redis.call(\"del\",KEYS[1])\n" +
                        "else\n" +
                        "    return 0\n" +
                        "end";
                Long execute = redisTemplate.execute(new DefaultRedisScript<>(script, Long.class), Arrays.asList("productLock"), productLock);
                return catalogJsonFromDB;
            }

        } else {
            System.out.println("get lock failed");
            try {
                //让线程睡眠100毫秒，防止调用方法过于频繁，栈空间溢出
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return getCatalogMapRedisLock();
        }
    }

    /**
     * 使用本地锁解决缓存击穿问题
     *
     * @return
     */
    public Map<String, List<Catalog2Vo>> getCatalogMap() {

        /***
         * 优化:将数据库的多次查询改为一次
         */
        //判断缓存中是否有数据，没有就从数据库中获取
        String catalogJson = redisTemplate.opsForValue().get("catalogJson");
        if (!StringUtils.isEmpty(catalogJson)) {
            Map<String, List<Catalog2Vo>> map = JSON.parseObject(catalogJson, new TypeReference<Map<String, List<Catalog2Vo>>>() {
            });
            return map;
        }
        return getCatalogJsonFromDBLock();
    }

    /**
     * 从数据库中查询三级分类数据
     *
     * @return
     */
    private Map<String, List<Catalog2Vo>> getCatalogJsonFromDBLock() {
        synchronized (this) {
            return getCatalogJsonFromDB();
        }
    }

    private Map<String, List<Catalog2Vo>> getCatalogJsonFromDB() {
        //再次判断缓存中是否有数据
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        String catalogJson = ops.get("catalogJson");
        if (!StringUtils.isEmpty(catalogJson)) {
            System.out.println("get data from redis");
            Map<String, List<Catalog2Vo>> map = JSON.parseObject(catalogJson, new TypeReference<Map<String, List<Catalog2Vo>>>() {
            });
            return map;
        }
        System.out.println("get data from db");
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
        //数据传输到redis中保存也需要花时间，所以在释放锁前保存数据，防止释放锁后数据还未保存好，其他线程又进来
        String toJSONString = JSON.toJSONString(collect);
        ops.set("catalogJson", toJSONString, 1L, TimeUnit.DAYS);
        return collect;

    }


    private List<CategoryEntity> getCidByParentCid(List<CategoryEntity> categoryList, Long cid) {
        List<CategoryEntity> collect = categoryList.stream().filter(item -> item.getParentCid() == cid).collect(Collectors.toList());
        return collect;
    }

    public List<Long> findParentPath(long catalogId, List<Long> path) {
        path.add(catalogId);
        CategoryEntity category = categoryDao.selectById(catalogId);
        if (category.getParentCid() != 0) {
            findParentPath(category.getParentCid(), path);
        }
        return path;
    }

    public List<CategoryEntity> getChildren(CategoryEntity root, List<CategoryEntity> all) {
        List<CategoryEntity> children = all.stream().filter(categoryEntity -> {
            if ((categoryEntity.getParentCid().equals(root.getCatId())) && (root.getCatId() == 1441L)) {
                System.out.println(categoryEntity);
                System.out.println(root);
            }
            return categoryEntity.getParentCid().equals(root.getCatId());
        }).map(categoryEntity -> {
            //1、找到子菜单
            if (categoryEntity.getName().equals("44412")) {
                System.out.println(categoryEntity);
            }
            categoryEntity.setChildren(getChildren(categoryEntity, all));
            return categoryEntity;
        }).sorted((menu1, menu2) -> {
            //2、菜单的排序
            return (menu1.getSort() == null ? 0 : menu1.getSort()) - (menu2.getSort() == null ? 0 : menu2.getSort());
        }).collect(Collectors.toList());

        return children;
    }
}
