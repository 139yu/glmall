package com.xj.glmall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.member.entity.MemberRuleSettingEntity;

import java.util.Map;

/**
 * 会员积分成长规则表
 *
 * @author yu
 * @email yu
 * @date 2020-06-24 20:01:36
 */
public interface MemberRuleSettingService extends IService<MemberRuleSettingEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

