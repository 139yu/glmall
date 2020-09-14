package com.xj.glmall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.coupon.entity.CouponSpuCategoryRelationEntity;

import java.util.Map;

/**
 * 优惠券分类关联
 *
 * @author yu
 * @email ${email}
 * @date 2020-09-08 22:33:12
 */
public interface CouponSpuCategoryRelationService extends IService<CouponSpuCategoryRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

