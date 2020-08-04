package com.xj.glmall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.coupon.entity.FlashPromotionEntity;

import java.util.Map;

/**
 * 限时购表
 *
 * @author yu
 * @email ${email}
 * @date 2020-06-23 23:32:29
 */
public interface FlashPromotionService extends IService<FlashPromotionEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

