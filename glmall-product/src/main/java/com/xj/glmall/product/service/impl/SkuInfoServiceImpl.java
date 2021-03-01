package com.xj.glmall.product.service.impl;

import com.xj.glmall.common.to.SkuInfoTo;
import com.xj.glmall.product.entity.*;
import com.xj.glmall.product.service.*;
import com.xj.glmall.product.vo.SkuItemSaleAttrVo;
import com.xj.glmall.product.vo.SkuItemVo;
import com.xj.glmall.product.vo.SpuItemBaseAttrVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.common.utils.Query;

import com.xj.glmall.product.dao.SkuInfoDao;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;


@Service("skuInfoService")
public class SkuInfoServiceImpl extends ServiceImpl<SkuInfoDao, SkuInfoEntity> implements SkuInfoService {

    @Autowired
    private AttrGroupService attrGroupService;

    @Autowired
    private SkuImagesService skuImagesService;

    @Autowired
    private SkuSaleAttrValueService skuSaleAttrValueService;

    @Autowired
    private SpuInfoDescService spuInfoDescService;

    @Autowired
    private ThreadPoolExecutor executor;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SkuInfoEntity> page = this.page(
                new Query<SkuInfoEntity>().getPage(params),
                new QueryWrapper<SkuInfoEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveSkuInfo(SkuInfoEntity skuInfoEntity) {
        this.baseMapper.insert(skuInfoEntity);
    }

    @Override
    public PageUtils queryPageByCondition(Map<String, Object> params) {
        String catalogId = (String) params.get("catalogId");
        String brandId = (String) params.get("brandId");
        QueryWrapper<SkuInfoEntity> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(catalogId) && !"0".equalsIgnoreCase(catalogId)) {
            wrapper.eq("catalog_id", catalogId);
        }
        if (!StringUtils.isEmpty(brandId) && !"0".equalsIgnoreCase(brandId)) {
            wrapper.eq("brand_id", brandId);
        }
        String key = (String) params.get("key");
        if (!StringUtils.isEmpty(key)) {
            wrapper.and(obj -> {
                obj.eq("sku_id", key).or().like("sku_name", key);
            });
        }
        String min = (String) params.get("min");
        if (!StringUtils.isEmpty(min)) {
            wrapper.ge("price", new BigDecimal(min));
        }
        String max = (String) params.get("max");
        if (!StringUtils.isEmpty("max")) {
            BigDecimal bigDecimal = new BigDecimal(max);
            if (bigDecimal.compareTo(new BigDecimal("0")) == 1) {
                wrapper.le("price", max);
            }
        }
        IPage<SkuInfoEntity> page = this.page(
                new Query<SkuInfoEntity>().getPage(params),
                wrapper
        );
        return new PageUtils(page);
    }

    @Override
    public void setSaleCount(SkuInfoTo to) {
        this.baseMapper.setSaleCount(to.getSkuId(), to.getSaleCount());
    }

    @Override
    public List<SkuInfoEntity> getSkuInfoBySpuId(Long spuId) {
        List<SkuInfoEntity> list = this.baseMapper.selectList(new QueryWrapper<SkuInfoEntity>().eq("spu_id", spuId));
        return list;
    }

    @Override
    public SkuItemVo getSkuItem(Long skuId) throws ExecutionException, InterruptedException {
        SkuItemVo skuItemVo = new SkuItemVo();
        CompletableFuture<SkuInfoEntity> infoFuture = CompletableFuture.supplyAsync(() -> {
            //1.sku基本信息 psm_sku_info
            SkuInfoEntity info = baseMapper.selectById(skuId);
            skuItemVo.setInfo(info);
            return info;
        }, executor);
        CompletableFuture<Void> saleAttrFuture = infoFuture.thenAcceptAsync(res -> {
            //3.sku的销售属性组合
            List<SkuItemSaleAttrVo> skuItemSaleAttrVos = skuSaleAttrValueService.listSkuSaleAttrValueBySpuId(res.getSpuId());
            skuItemVo.setSaleAttrs(skuItemSaleAttrVos);
        }, executor);
        CompletableFuture<Void> descFuture = infoFuture.thenAcceptAsync(res -> {
            //4.spu的介绍
            SpuInfoDescEntity spuInfoDescEntity = spuInfoDescService.getById(res.getSpuId());
            skuItemVo.setDesp(spuInfoDescEntity);
        }, executor);
        CompletableFuture<Void> baseAttrFuture = infoFuture.thenAcceptAsync(res -> {
            //5.spu的规格参数
            List<SpuItemBaseAttrVo> spuItemBaseAttrVos = attrGroupService.listAttrGroupWithAttrsBySpuIdAndCatalogId(res.getSpuId(), res.getCatalogId());
            skuItemVo.setBaseAttrs(spuItemBaseAttrVos);
        }, executor);
        CompletableFuture<Void> imagesFuture = CompletableFuture.runAsync(() -> {
            //2.sku的图片信息，psm_sku_images
            List<SkuImagesEntity> skuImagesEntities = skuImagesService.listSkuImageBySkuId(skuId);
            skuItemVo.setImages(skuImagesEntities);
        });
        CompletableFuture.allOf(saleAttrFuture,descFuture,baseAttrFuture,imagesFuture).get();
        return skuItemVo;
    }

}
