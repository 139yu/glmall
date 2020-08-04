package com.xj.glmall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.member.entity.IntegrationConsumeSettingEntity;

import java.util.Map;

/**
 * 积分消费设置
 *
 * @author yu
 * @email yu
 * @date 2020-06-24 20:01:37
 */
public interface IntegrationConsumeSettingService extends IService<IntegrationConsumeSettingEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

