package com.xj.glmall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.coupon.entity.CouponHistoryEntity;

import java.util.Map;

/**
 * 优惠券使用、领取历史表
 *
 * @author yu
 * @email ${email}
 * @date 2020-06-23 23:32:29
 */
public interface CouponHistoryService extends IService<CouponHistoryEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

