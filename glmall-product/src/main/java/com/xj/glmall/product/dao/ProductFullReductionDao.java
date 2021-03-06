package com.xj.glmall.product.dao;

import com.xj.glmall.product.entity.ProductFullReductionEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 产品满减表(只针对同商品)
 * 
 * @author yu
 * @email ${email}
 * @date 2020-07-02 22:57:13
 */
@Mapper
public interface ProductFullReductionDao extends BaseMapper<ProductFullReductionEntity> {
	
}
