package com.xj.glmall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xj.glmall.common.to.SkuReductionTo;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.coupon.entity.SkuFullReductionEntity;

import java.util.Map;

/**
 * 商品满减信息
 *
 * @author yu
 * @email ${email}
 * @date 2020-09-08 22:33:12
 */
public interface SkuFullReductionService extends IService<SkuFullReductionEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveReductionInfo(SkuReductionTo skuReductionTo);
}

