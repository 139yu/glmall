package com.xj.glmall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.ware.entity.WareSkuEntity;

import java.util.Map;

/**
 * 
 *
 * @author yu
 * @email yu
 * @date 2020-06-24 20:15:57
 */
public interface WareSkuService extends IService<WareSkuEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

