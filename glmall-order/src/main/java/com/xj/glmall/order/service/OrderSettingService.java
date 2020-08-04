package com.xj.glmall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.order.entity.OrderSettingEntity;

import java.util.Map;

/**
 * 订单设置表
 *
 * @author yu
 * @email ${email}
 * @date 2020-06-24 00:05:19
 */
public interface OrderSettingService extends IService<OrderSettingEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

