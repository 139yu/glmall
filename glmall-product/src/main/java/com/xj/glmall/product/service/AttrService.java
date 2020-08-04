package com.xj.glmall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.product.entity.AttrEntity;

import java.util.Map;

/**
 * 商品属性
 *
 * @author yu
 * @email ${email}
 * @date 2020-07-28 22:29:04
 */
public interface AttrService extends IService<AttrEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

