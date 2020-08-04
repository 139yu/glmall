package com.xj.glmall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.member.entity.AdminLoginLogEntity;

import java.util.Map;

/**
 * 后台用户登录日志表
 *
 * @author yu
 * @email yu
 * @date 2020-06-24 20:01:37
 */
public interface AdminLoginLogService extends IService<AdminLoginLogEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

