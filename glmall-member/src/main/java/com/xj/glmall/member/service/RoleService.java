package com.xj.glmall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.member.entity.RoleEntity;

import java.util.Map;

/**
 * 后台用户角色表
 *
 * @author yu
 * @email yu
 * @date 2020-06-24 20:01:37
 */
public interface RoleService extends IService<RoleEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

