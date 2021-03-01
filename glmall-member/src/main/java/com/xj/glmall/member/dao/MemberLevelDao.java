package com.xj.glmall.member.dao;

import com.xj.glmall.member.entity.MemberLevelEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 会员等级
 * 
 * @author yu
 * @email ${email}
 * @date 2020-10-21 22:14:15
 */
@Mapper
public interface MemberLevelDao extends BaseMapper<MemberLevelEntity> {
    MemberLevelEntity getDefaultLevel(@Param("defaultStatus") int defaultStatus);
}
