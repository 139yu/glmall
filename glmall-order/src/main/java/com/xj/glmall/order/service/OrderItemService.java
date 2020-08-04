package com.xj.glmall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.order.entity.OrderItemEntity;

import java.util.Map;

/**
 * 订单中所包含的商品
 *
 * @author yu
 * @email ${email}
 * @date 2020-06-24 00:05:20
 */
public interface OrderItemService extends IService<OrderItemEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

