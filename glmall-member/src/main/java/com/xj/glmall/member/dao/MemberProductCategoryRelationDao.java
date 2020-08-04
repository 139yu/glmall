package com.xj.glmall.member.dao;

import com.xj.glmall.member.entity.MemberProductCategoryRelationEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员与产品分类关系表（用户喜欢的分类）
 * 
 * @author yu
 * @email yu
 * @date 2020-06-24 20:01:36
 */
@Mapper
public interface MemberProductCategoryRelationDao extends BaseMapper<MemberProductCategoryRelationEntity> {
	
}
