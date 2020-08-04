package com.xj.glmall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.coupon.entity.HomeRecommendSubjectEntity;

import java.util.Map;

/**
 * 首页推荐专题表
 *
 * @author yu
 * @email ${email}
 * @date 2020-06-23 23:32:29
 */
public interface HomeRecommendSubjectService extends IService<HomeRecommendSubjectEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

