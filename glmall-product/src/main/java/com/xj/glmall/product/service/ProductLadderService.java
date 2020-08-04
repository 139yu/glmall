package com.xj.glmall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.product.entity.ProductLadderEntity;

import java.util.Map;

/**
 * 产品阶梯价格表(只针对同商品)
 *
 * @author yu
 * @email ${email}
 * @date 2020-07-02 22:57:13
 */
public interface ProductLadderService extends IService<ProductLadderEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

