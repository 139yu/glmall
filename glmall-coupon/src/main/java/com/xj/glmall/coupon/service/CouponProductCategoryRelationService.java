package com.xj.glmall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.coupon.entity.CouponProductCategoryRelationEntity;

import java.util.Map;

/**
 * 优惠券和产品分类关系表
 *
 * @author yu
 * @email ${email}
 * @date 2020-06-23 23:32:29
 */
public interface CouponProductCategoryRelationService extends IService<CouponProductCategoryRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

