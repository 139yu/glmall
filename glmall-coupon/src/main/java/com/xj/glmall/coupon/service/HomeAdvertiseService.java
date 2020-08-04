package com.xj.glmall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.coupon.entity.HomeAdvertiseEntity;

import java.util.Map;

/**
 * 首页轮播广告表
 *
 * @author yu
 * @email ${email}
 * @date 2020-06-23 23:32:29
 */
public interface HomeAdvertiseService extends IService<HomeAdvertiseEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

