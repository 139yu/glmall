package com.xj.glmall.order.dao;

import com.xj.glmall.order.entity.CartItemEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 购物车表
 * 
 * @author yu
 * @email ${email}
 * @date 2020-06-24 00:05:20
 */
@Mapper
public interface CartItemDao extends BaseMapper<CartItemEntity> {
	
}
