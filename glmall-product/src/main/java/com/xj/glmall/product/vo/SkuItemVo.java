package com.xj.glmall.product.vo;

import com.xj.glmall.product.entity.SkuImagesEntity;
import com.xj.glmall.product.entity.SkuInfoEntity;
import com.xj.glmall.product.entity.SpuInfoDescEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SkuItemVo implements Serializable {
    //1.sku基本信息 psm_sku_info
    private SkuInfoEntity info;

    //2.sku的图片信息，psm_sku_images
    private List<SkuImagesEntity> images;

    //3.sku的销售属性组合
    private List<SkuItemSaleAttrVo> saleAttrs;

    //4.spu的介绍
    private SpuInfoDescEntity desp;

    //5.spu的规格参数
    private List<SpuItemBaseAttrVo> baseAttrs;

    private Boolean hasStock = true;

}
