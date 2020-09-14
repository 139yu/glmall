package com.xj.glmall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.product.entity.SkuInfoEntity;
import com.xj.glmall.product.vo.Skus;

import java.util.List;
import java.util.Map;

/**
 * sku信息
 *
 * @author yu
 * @email ${email}
 * @date 2020-07-28 22:29:04
 */
public interface SkuInfoService extends IService<SkuInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveSkuInfo(SkuInfoEntity skuInfoEntity);

    PageUtils queryPageByCondition(Map<String, Object> params);
}

