package com.xj.glmall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.product.entity.ProductFullReductionEntity;

import java.util.Map;

/**
 * 产品满减表(只针对同商品)
 *
 * @author yu
 * @email ${email}
 * @date 2020-07-02 22:57:13
 */
public interface ProductFullReductionService extends IService<ProductFullReductionEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

