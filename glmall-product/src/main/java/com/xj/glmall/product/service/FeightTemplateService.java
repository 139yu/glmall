package com.xj.glmall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.product.entity.FeightTemplateEntity;

import java.util.Map;

/**
 * 运费模版
 *
 * @author yu
 * @email ${email}
 * @date 2020-07-02 22:57:13
 */
public interface FeightTemplateService extends IService<FeightTemplateEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

