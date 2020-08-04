package com.xj.glmall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.coupon.entity.FlashPromotionProductRelationEntity;

import java.util.Map;

/**
 * 商品限时购与商品关系表
 *
 * @author yu
 * @email ${email}
 * @date 2020-06-23 23:32:29
 */
public interface FlashPromotionProductRelationService extends IService<FlashPromotionProductRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

