package com.xj.glmall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.product.entity.ProductSaleAttrEntity;

import java.util.Map;

/**
 * 
 *
 * @author yu
 * @email ${email}
 * @date 2020-07-02 22:57:13
 */
public interface ProductSaleAttrService extends IService<ProductSaleAttrEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

