package com.xj.glmall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.coupon.entity.CouponProductRelationEntity;

import java.util.Map;

/**
 * 优惠券和产品的关系表
 *
 * @author yu
 * @email ${email}
 * @date 2020-06-23 23:32:29
 */
public interface CouponProductRelationService extends IService<CouponProductRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

