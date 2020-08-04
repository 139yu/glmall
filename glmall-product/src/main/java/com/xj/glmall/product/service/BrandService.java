package com.xj.glmall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.product.entity.BrandEntity;

import java.util.Map;

/**
 * 品牌
 *
 * @author yu
 * @email ${email}
 * @date 2020-07-28 22:29:04
 */
public interface BrandService extends IService<BrandEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

