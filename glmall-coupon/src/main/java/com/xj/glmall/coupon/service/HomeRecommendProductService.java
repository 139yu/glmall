package com.xj.glmall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.coupon.entity.HomeRecommendProductEntity;

import java.util.Map;

/**
 * 人气推荐商品表
 *
 * @author yu
 * @email ${email}
 * @date 2020-06-23 23:32:29
 */
public interface HomeRecommendProductService extends IService<HomeRecommendProductEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

