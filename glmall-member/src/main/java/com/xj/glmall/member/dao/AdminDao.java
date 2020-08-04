package com.xj.glmall.member.dao;

import com.xj.glmall.member.entity.AdminEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 后台用户表
 * 
 * @author yu
 * @email yu
 * @date 2020-06-24 20:01:37
 */
@Mapper
public interface AdminDao extends BaseMapper<AdminEntity> {
	
}
