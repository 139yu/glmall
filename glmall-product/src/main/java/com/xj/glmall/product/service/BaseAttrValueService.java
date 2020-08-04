package com.xj.glmall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.product.entity.BaseAttrValueEntity;

import java.util.Map;

/**
 * 属性值表
 *
 * @author yu
 * @email ${email}
 * @date 2020-07-02 22:57:14
 */
public interface BaseAttrValueService extends IService<BaseAttrValueEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

