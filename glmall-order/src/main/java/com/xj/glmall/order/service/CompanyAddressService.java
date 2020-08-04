package com.xj.glmall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.order.entity.CompanyAddressEntity;

import java.util.Map;

/**
 * 公司收发货地址表
 *
 * @author yu
 * @email ${email}
 * @date 2020-06-24 00:05:20
 */
public interface CompanyAddressService extends IService<CompanyAddressEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

