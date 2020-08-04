package com.xj.glmall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.order.entity.OrderEntity;

import java.util.Map;

/**
 * 订单表
 *
 * @author yu
 * @email ${email}
 * @date 2020-06-24 00:05:20
 */
public interface OrderService extends IService<OrderEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

