package com.xj.glmall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.coupon.entity.HomeNewProductEntity;

import java.util.Map;

/**
 * 新鲜好物表
 *
 * @author yu
 * @email ${email}
 * @date 2020-06-23 23:32:29
 */
public interface HomeNewProductService extends IService<HomeNewProductEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

