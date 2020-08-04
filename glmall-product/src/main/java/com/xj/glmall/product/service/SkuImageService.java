package com.xj.glmall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.product.entity.SkuImageEntity;

import java.util.Map;

/**
 * 库存单元图片表
 *
 * @author yu
 * @email ${email}
 * @date 2020-07-02 22:57:13
 */
public interface SkuImageService extends IService<SkuImageEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

