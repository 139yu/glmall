package com.xj.glmall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.coupon.entity.HomeSubjectSpuEntity;

import java.util.Map;

/**
 * 专题商品
 *
 * @author yu
 * @email ${email}
 * @date 2020-09-08 22:33:12
 */
public interface HomeSubjectSpuService extends IService<HomeSubjectSpuEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

