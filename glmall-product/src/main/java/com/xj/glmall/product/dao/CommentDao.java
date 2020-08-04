package com.xj.glmall.product.dao;

import com.xj.glmall.product.entity.CommentEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品评价表
 * 
 * @author yu
 * @email ${email}
 * @date 2020-07-02 22:57:13
 */
@Mapper
public interface CommentDao extends BaseMapper<CommentEntity> {
	
}
