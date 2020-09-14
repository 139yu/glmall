package com.xj.glmall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.ware.entity.PurchaseDetailEntity;

import java.util.Map;

/**
 *
 *
 * @author yu
 * @email ${email}
 * @date 2020-09-14 22:29:45
 */
public interface PurchaseDetailService extends IService<PurchaseDetailEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

