package com.xj.glmall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.product.entity.ProductVertifyRecordEntity;

import java.util.Map;

/**
 * 商品审核记录
 *
 * @author yu
 * @email ${email}
 * @date 2020-07-02 22:57:13
 */
public interface ProductVertifyRecordService extends IService<ProductVertifyRecordEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

