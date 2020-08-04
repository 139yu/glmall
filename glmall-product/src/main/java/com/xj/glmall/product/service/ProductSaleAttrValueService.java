package com.xj.glmall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.product.entity.ProductSaleAttrValueEntity;

import java.util.Map;

/**
 * spu销售属性值
 *
 * @author yu
 * @email ${email}
 * @date 2020-07-02 22:57:13
 */
public interface ProductSaleAttrValueService extends IService<ProductSaleAttrValueEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

