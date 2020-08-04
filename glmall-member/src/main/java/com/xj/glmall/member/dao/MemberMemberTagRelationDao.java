package com.xj.glmall.member.dao;

import com.xj.glmall.member.entity.MemberMemberTagRelationEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户和标签关系表
 * 
 * @author yu
 * @email yu
 * @date 2020-06-24 20:01:36
 */
@Mapper
public interface MemberMemberTagRelationDao extends BaseMapper<MemberMemberTagRelationEntity> {
	
}
