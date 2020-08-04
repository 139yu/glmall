package com.xj.glmall.member.dao;

import com.xj.glmall.member.entity.AdminPermissionRelationEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 后台用户和权限关系表(除角色中定义的权限以外的加减权限)
 * 
 * @author yu
 * @email yu
 * @date 2020-06-24 20:01:37
 */
@Mapper
public interface AdminPermissionRelationDao extends BaseMapper<AdminPermissionRelationEntity> {
	
}
