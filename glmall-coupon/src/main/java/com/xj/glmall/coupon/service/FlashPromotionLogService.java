package com.xj.glmall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.coupon.entity.FlashPromotionLogEntity;

import java.util.Map;

/**
 * 限时购通知记录
 *
 * @author yu
 * @email ${email}
 * @date 2020-06-23 23:32:29
 */
public interface FlashPromotionLogService extends IService<FlashPromotionLogEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

