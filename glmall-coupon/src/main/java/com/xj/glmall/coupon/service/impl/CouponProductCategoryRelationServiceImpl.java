package com.xj.glmall.coupon.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.common.utils.Query;

import com.xj.glmall.coupon.dao.CouponProductCategoryRelationDao;
import com.xj.glmall.coupon.entity.CouponProductCategoryRelationEntity;
import com.xj.glmall.coupon.service.CouponProductCategoryRelationService;


@Service("couponProductCategoryRelationService")
public class CouponProductCategoryRelationServiceImpl extends ServiceImpl<CouponProductCategoryRelationDao, CouponProductCategoryRelationEntity> implements CouponProductCategoryRelationService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CouponProductCategoryRelationEntity> page = this.page(
                new Query<CouponProductCategoryRelationEntity>().getPage(params),
                new QueryWrapper<CouponProductCategoryRelationEntity>()
        );

        return new PageUtils(page);
    }

}