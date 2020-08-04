package com.xj.glmall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.member.entity.MemberReceiveAddressEntity;

import java.util.Map;

/**
 * 会员收货地址表
 *
 * @author yu
 * @email yu
 * @date 2020-06-24 20:01:36
 */
public interface MemberReceiveAddressService extends IService<MemberReceiveAddressEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

