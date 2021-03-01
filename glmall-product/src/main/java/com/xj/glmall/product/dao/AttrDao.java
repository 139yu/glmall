package com.xj.glmall.product.dao;

import com.xj.glmall.product.entity.AttrEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品属性
 * 
 * @author yu
 * @email ${email}
 * @date 2020-07-28 22:29:04
 */
@Mapper
public interface AttrDao extends BaseMapper<AttrEntity> {

    List<Long> selectSearchAttrIds(@Param("attrIds") List<Long> attrIds);

    List<AttrEntity> selectAttrByGroupId(@Param("groupId") Long groupId);
}
