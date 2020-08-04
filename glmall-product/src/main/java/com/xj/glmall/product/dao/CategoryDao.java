package com.xj.glmall.product.dao;

import com.xj.glmall.product.entity.CategoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品三级分类
 * 
 * @author yu
 * @email ${email}
 * @date 2020-07-02 22:57:13
 */
@Mapper
public interface CategoryDao extends BaseMapper<CategoryEntity> {
	
}
