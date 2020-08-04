package com.xj.glmall.order.dao;

import com.xj.glmall.order.entity.OrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单表
 * 
 * @author yu
 * @email ${email}
 * @date 2020-06-24 00:05:20
 */
@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {
	
}
