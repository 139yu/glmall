package com.xj.glmall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.product.entity.SkuAttrValueEntity;

import java.util.Map;

/**
 * sku平台属性值关联表
 *
 * @author yu
 * @email ${email}
 * @date 2020-07-02 22:57:13
 */
public interface SkuAttrValueService extends IService<SkuAttrValueEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

