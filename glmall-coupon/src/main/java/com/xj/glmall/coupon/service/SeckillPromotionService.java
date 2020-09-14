package com.xj.glmall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.coupon.entity.SeckillPromotionEntity;

import java.util.Map;

/**
 * 秒杀活动
 *
 * @author yu
 * @email ${email}
 * @date 2020-09-08 22:33:12
 */
public interface SeckillPromotionService extends IService<SeckillPromotionEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

