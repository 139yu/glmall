package com.xj.glmall.coupon.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.common.utils.Query;

import com.xj.glmall.coupon.dao.HomeRecommendProductDao;
import com.xj.glmall.coupon.entity.HomeRecommendProductEntity;
import com.xj.glmall.coupon.service.HomeRecommendProductService;


@Service("homeRecommendProductService")
public class HomeRecommendProductServiceImpl extends ServiceImpl<HomeRecommendProductDao, HomeRecommendProductEntity> implements HomeRecommendProductService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<HomeRecommendProductEntity> page = this.page(
                new Query<HomeRecommendProductEntity>().getPage(params),
                new QueryWrapper<HomeRecommendProductEntity>()
        );

        return new PageUtils(page);
    }

}