package com.xj.glmall.member.dao;

import com.xj.glmall.member.entity.RolePermissionRelationEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 后台用户角色和权限关系表
 * 
 * @author yu
 * @email yu
 * @date 2020-06-24 20:01:37
 */
@Mapper
public interface RolePermissionRelationDao extends BaseMapper<RolePermissionRelationEntity> {
	
}
