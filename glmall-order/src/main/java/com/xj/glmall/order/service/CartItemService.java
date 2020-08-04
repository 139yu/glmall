package com.xj.glmall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.order.entity.CartItemEntity;

import java.util.Map;

/**
 * 购物车表
 *
 * @author yu
 * @email ${email}
 * @date 2020-06-24 00:05:20
 */
public interface CartItemService extends IService<CartItemEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

