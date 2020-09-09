package com.xj.glmall.product.dao;

import com.xj.glmall.product.entity.SpuInfoDescEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * spu信息介绍
 *
 * @author yu
 * @email ${email}
 * @date 2020-07-28 22:29:04
 */
@Mapper
public interface SpuInfoDescDao extends BaseMapper<SpuInfoDescEntity> {

    void insertList(List<SpuInfoDescEntity> descEntities);
}
