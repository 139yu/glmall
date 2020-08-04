package com.xj.glmall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.product.entity.BaseCatalog3Entity;

import java.util.Map;

/**
 * 
 *
 * @author yu
 * @email ${email}
 * @date 2020-07-02 22:57:14
 */
public interface BaseCatalog3Service extends IService<BaseCatalog3Entity> {

    PageUtils queryPage(Map<String, Object> params);
}

