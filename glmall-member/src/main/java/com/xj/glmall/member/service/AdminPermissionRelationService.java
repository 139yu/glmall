package com.xj.glmall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.member.entity.AdminPermissionRelationEntity;

import java.util.Map;

/**
 * 后台用户和权限关系表(除角色中定义的权限以外的加减权限)
 *
 * @author yu
 * @email yu
 * @date 2020-06-24 20:01:37
 */
public interface AdminPermissionRelationService extends IService<AdminPermissionRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

