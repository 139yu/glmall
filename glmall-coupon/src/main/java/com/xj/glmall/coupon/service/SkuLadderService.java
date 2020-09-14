package com.xj.glmall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.coupon.entity.SkuLadderEntity;

import java.util.Map;

/**
 * 商品阶梯价格
 *
 * @author yu
 * @email ${email}
 * @date 2020-09-08 22:33:12
 */
public interface SkuLadderService extends IService<SkuLadderEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

