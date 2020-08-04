package com.xj.glmall.coupon.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.common.utils.Query;

import com.xj.glmall.coupon.dao.CouponProductRelationDao;
import com.xj.glmall.coupon.entity.CouponProductRelationEntity;
import com.xj.glmall.coupon.service.CouponProductRelationService;


@Service("couponProductRelationService")
public class CouponProductRelationServiceImpl extends ServiceImpl<CouponProductRelationDao, CouponProductRelationEntity> implements CouponProductRelationService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CouponProductRelationEntity> page = this.page(
                new Query<CouponProductRelationEntity>().getPage(params),
                new QueryWrapper<CouponProductRelationEntity>()
        );

        return new PageUtils(page);
    }

}