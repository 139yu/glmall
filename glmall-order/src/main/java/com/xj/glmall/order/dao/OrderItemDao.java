package com.xj.glmall.order.dao;

import com.xj.glmall.order.entity.OrderItemEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单中所包含的商品
 * 
 * @author yu
 * @email ${email}
 * @date 2020-06-24 00:05:20
 */
@Mapper
public interface OrderItemDao extends BaseMapper<OrderItemEntity> {
	
}
