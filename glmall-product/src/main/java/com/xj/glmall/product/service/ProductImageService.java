package com.xj.glmall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.product.entity.ProductImageEntity;

import java.util.Map;

/**
 * 商品图片表
 *
 * @author yu
 * @email ${email}
 * @date 2020-07-02 22:57:13
 */
public interface ProductImageService extends IService<ProductImageEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

