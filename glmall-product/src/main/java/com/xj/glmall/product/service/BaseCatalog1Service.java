package com.xj.glmall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.product.entity.BaseCatalog1Entity;

import java.util.Map;

/**
 * 一级分类表
 *
 * @author yu
 * @email ${email}
 * @date 2020-07-02 22:57:14
 */
public interface BaseCatalog1Service extends IService<BaseCatalog1Entity> {

    PageUtils queryPage(Map<String, Object> params);
}

