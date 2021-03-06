package com.xj.glmall.product.dao;

import com.xj.glmall.product.entity.SkuInfoEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * sku信息
 *
 * @author yu
 * @email ${email}
 * @date 2020-07-28 22:29:04
 */
@Mapper
public interface SkuInfoDao extends BaseMapper<SkuInfoEntity> {

    void setSaleCount(@Param("skuId") Long skuId, @Param("saleCount") Long saleCount);
}
