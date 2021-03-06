package com.xj.glmall.product.service.impl;


import com.alibaba.fastjson.TypeReference;
import com.xj.glmall.common.constant.ProductConstant;
import com.xj.glmall.common.to.BoundsTo;
import com.xj.glmall.common.to.SkuReductionTo;
import com.xj.glmall.common.to.es.SkuEsModel;
import com.xj.glmall.common.utils.R;
import com.xj.glmall.product.dao.SpuInfoDescDao;
import com.xj.glmall.product.entity.*;
import com.xj.glmall.product.feign.CouponFeignService;
import com.xj.glmall.product.feign.SearchFeignService;
import com.xj.glmall.product.feign.WareFeignService;
import com.xj.glmall.product.service.*;
import com.xj.glmall.product.vo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.common.utils.Query;

import com.xj.glmall.product.dao.SpuInfoDao;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


@Service("spuInfoService")
public class SpuInfoServiceImpl extends ServiceImpl<SpuInfoDao, SpuInfoEntity> implements SpuInfoService {

    @Autowired
    private SpuInfoDescDao spuInfoDescDao;
    @Autowired
    private SpuImagesService spuImagesService;
    @Autowired
    private ProductAttrValueService productAttrValueService;
    @Autowired
    private SkuInfoService skuInfoService;
    @Autowired
    private SkuImageService skuImageService;
    @Autowired
    private SkuSaleAttrValueService skuSaleAttrValueService;
    @Autowired
    private CouponFeignService couponFeignService;
    @Autowired
    private BrandService brandService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private WareFeignService wareFeignService;
    @Autowired
    private AttrService attrService;
    @Autowired
    private SearchFeignService searchFeignService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SpuInfoEntity> page = this.page(
                new Query<SpuInfoEntity>().getPage(params),
                new QueryWrapper<SpuInfoEntity>()
        );

        return new PageUtils(page);
    }
    @Override
    @Transactional
    public void saveSpuInfo(SpuSaveVo saveVo) {
        //保存spu信息
        SpuInfoEntity spuInfo = new SpuInfoEntity();
        spuInfo.setUpdateTime(new Date());
        spuInfo.setCreateTime(new Date());
        BeanUtils.copyProperties(saveVo, spuInfo);
        this.baseMapper.insert(spuInfo);
        Long spuId = spuInfo.getId();
        //保持spu描述图片url
        SpuInfoDescEntity descEntity = new SpuInfoDescEntity();
        descEntity.setSpuId(spuId);
        descEntity.setDecript(String.join(",",saveVo.getDecript()));
        spuInfoDescDao.insert(descEntity);
        //保存spu图片信息
        List<String> images = saveVo.getImages();
        spuImagesService.saveImages(spuId,images);
        //保存spu的规格参数
        List<BaseAttrs> baseAttrs = saveVo.getBaseAttrs();
        productAttrValueService.saveAttrs(spuId,baseAttrs);
        //保存会员积分信息
        Bounds bounds = saveVo.getBounds();
        BoundsTo boundsTo = new BoundsTo();
        BeanUtils.copyProperties(bounds,boundsTo);
        boundsTo.setSpuId(spuId);
        R res = couponFeignService.saveSpuBounds(boundsTo);

        //保存sku信息
        List<Skus> skus = saveVo.getSkus();
        skus.forEach(item -> {
            String defaultImageUrl = null;
            List<Images> imagesList = item.getImages();
            for (Images img : imagesList) {
                if (img.getDefaultImg() == 1) {
                    defaultImageUrl = img.getImgUrl();
                }
            }
            SkuInfoEntity skuInfoEntity = new SkuInfoEntity();
            skuInfoEntity.setSpuId(spuId);
            BeanUtils.copyProperties(item,skuInfoEntity);
            skuInfoEntity.setBrandId(spuInfo.getBrandId());
            skuInfoEntity.setCatalogId(spuInfo.getCatalogId());
            skuInfoEntity.setSkuDefaultImg(defaultImageUrl);
            skuInfoService.saveSkuInfo(skuInfoEntity);
            Long skuId = skuInfoEntity.getSkuId();
            //保存sku图片
            List<SkuImageEntity> collect = imagesList.stream().map(obj -> {
                SkuImageEntity skuImageEntity = new SkuImageEntity();
                skuImageEntity.setImgUrl(obj.getImgUrl());
                skuImageEntity.setSkuId(skuId);
                skuImageEntity.setDefaultImg(obj.getDefaultImg());
                return skuImageEntity;
            }).filter(entity -> {
                return !StringUtils.isEmpty(entity.getImgUrl());
            }).collect(Collectors.toList());
            skuImageService.saveBatch(collect);
            //保存sku的销售属性
            List<Attr> attrList = item.getAttr();
            List<SkuSaleAttrValueEntity> saleAttrValueEntities = attrList.stream().map(attr -> {
                SkuSaleAttrValueEntity skuSaleAttrValueEntity = new SkuSaleAttrValueEntity();
                skuSaleAttrValueEntity.setSkuId(skuId);
                BeanUtils.copyProperties(attr, skuSaleAttrValueEntity);
                return skuSaleAttrValueEntity;
            }).collect(Collectors.toList());
            skuSaleAttrValueService.saveBatch(saleAttrValueEntities);
            //保存sku优惠信息
            if(item.getFullPrice().compareTo(new BigDecimal("0")) == 1|| item.getFullCount() > 0) {
                SkuReductionTo skuReductionTo = new SkuReductionTo();
                BeanUtils.copyProperties(item,skuReductionTo);
                skuReductionTo.setSkuId(skuId);
                couponFeignService.saveReductionInfo(skuReductionTo);
            }

        });

    }

    @Override
    public PageUtils queryPageByCondition(Map<String, Object> params) {
        IPage<SpuInfoEntity> page = this.page(
                new Query<SpuInfoEntity>().getPage(params),
                new QueryWrapper<SpuInfoEntity>()
        );

        return new PageUtils(page);
    }
    
    @Override
    public void up(Long spuId) {
        //设置可检索属性
        List<ProductAttrValueEntity> productAttrValueEntities = productAttrValueService.baseAttrListForSpuId(spuId);
        List<Long> attrIds = productAttrValueEntities.stream().map(item -> {
            return item.getAttrId();
        }).collect(Collectors.toList());
        List<Long> searchAttrIds = attrService.selectSearchAttrIds(attrIds);
        System.out.println(searchAttrIds);
        Set<Long> idSet = new HashSet<>(searchAttrIds);
        List<SkuEsModel.Attr> collect = productAttrValueEntities.stream().filter(item -> {
            return idSet.contains(item.getAttrId());
        }).map(obj -> {
            SkuEsModel.Attr attr = new SkuEsModel.Attr();
            attr.setAttrId(obj.getAttrId());
            attr.setAttrName(obj.getAttrName());
            attr.setAttrValue(obj.getAttrValue());
            return attr;
        }).collect(Collectors.toList());

        List<SkuInfoEntity> skuList = skuInfoService.getSkuInfoBySpuId(spuId);
        List<Long> skuIds = skuList.stream().map(SkuInfoEntity::getSkuId).collect(Collectors.toList());
        Map<Long, Boolean> hasStockMap = null;
        try {
            R<List<SkuHasStockVo>> skuHasStock = wareFeignService.getSkuHasStock(skuIds);
            TypeReference<List<SkuHasStockVo>> typeReference = new TypeReference<List<SkuHasStockVo>>() {
            };
            hasStockMap = skuHasStock.getData(typeReference).stream().collect(Collectors.toMap(SkuHasStockVo::getSkuId, SkuHasStockVo::getHasStack));
        }catch (Exception e) {
            log.error("库存服务查询异常：原因{}",e);
        }


        Map<Long, Boolean> finalHasStockMap = hasStockMap;
        List<SkuEsModel> skuEsModelList = skuList.stream().map(item -> {
            SkuEsModel skuEsModel = new SkuEsModel();
            BeanUtils.copyProperties(item, skuEsModel);
            //skuPrice,skuImg,hasStock,hotScore,brandName,BrandImg,catalogName,attr
            skuEsModel.setSkuPrice(item.getPrice());
            skuEsModel.setSkuImg(item.getSkuDefaultImg());
            //设置品牌名称
            BrandEntity brandEntity = brandService.getById(item.getBrandId());
            skuEsModel.setBrandName(brandEntity.getName());
            //设置品牌图片
            skuEsModel.setBrandImg(brandEntity.getLogo());
            //设置分类名
            CategoryEntity category = categoryService.getById(item.getCatalogId());
            skuEsModel.setCatalogName(category.getName());
            //设置attrs
            skuEsModel.setAttrs(collect);
            //TODO 设置是否有库存
            if (finalHasStockMap == null) {
                skuEsModel.setHasStock(true);
            } else {
                skuEsModel.setHasStock(finalHasStockMap.get(item.getSkuId()));
            }

            //TODO 设置商品热度
            skuEsModel.setHotScore(0L);
            return skuEsModel;
        }).collect(Collectors.toList());
        //TODO 将数据发送给es保存
        R r = searchFeignService.productSave(skuEsModelList);
        if (r.getCode() == 0) {
            baseMapper.updateSataus(spuId, ProductConstant.StatusEnum.SPU_UP.getCode());
        }else {
            //TODO 上架失败继续重复调用
        }
    }


}
