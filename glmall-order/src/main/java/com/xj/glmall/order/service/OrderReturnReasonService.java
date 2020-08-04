package com.xj.glmall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.order.entity.OrderReturnReasonEntity;

import java.util.Map;

/**
 * 退货原因表
 *
 * @author yu
 * @email ${email}
 * @date 2020-06-24 00:05:19
 */
public interface OrderReturnReasonService extends IService<OrderReturnReasonEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

