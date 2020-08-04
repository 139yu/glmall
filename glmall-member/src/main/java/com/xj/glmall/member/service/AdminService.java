package com.xj.glmall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.member.entity.AdminEntity;

import java.util.Map;

/**
 * 后台用户表
 *
 * @author yu
 * @email yu
 * @date 2020-06-24 20:01:37
 */
public interface AdminService extends IService<AdminEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

