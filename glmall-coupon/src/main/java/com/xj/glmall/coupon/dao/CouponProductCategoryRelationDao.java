package com.xj.glmall.coupon.dao;

import com.xj.glmall.coupon.entity.CouponProductCategoryRelationEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 优惠券和产品分类关系表
 * 
 * @author yu
 * @email ${email}
 * @date 2020-06-23 23:32:29
 */
@Mapper
public interface CouponProductCategoryRelationDao extends BaseMapper<CouponProductCategoryRelationEntity> {
	
}
