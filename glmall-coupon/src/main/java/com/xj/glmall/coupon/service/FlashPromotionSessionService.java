package com.xj.glmall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.coupon.entity.FlashPromotionSessionEntity;

import java.util.Map;

/**
 * 限时购场次表
 *
 * @author yu
 * @email ${email}
 * @date 2020-06-23 23:32:29
 */
public interface FlashPromotionSessionService extends IService<FlashPromotionSessionEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

