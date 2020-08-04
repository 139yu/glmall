package com.xj.glmall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.coupon.entity.HomeBrandEntity;

import java.util.Map;

/**
 * 首页推荐品牌表
 *
 * @author yu
 * @email ${email}
 * @date 2020-06-23 23:32:29
 */
public interface HomeBrandService extends IService<HomeBrandEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

