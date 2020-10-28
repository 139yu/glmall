package com.xj.glmall.ware.dao;

import com.xj.glmall.ware.entity.WareSkuEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 商品库存
 *
 * @author yu
 * @email ${email}
 * @date 2020-09-14 22:29:45
 */
@Mapper
public interface WareSkuDao extends BaseMapper<WareSkuEntity> {

    void addStock(@Param("skuId") Long skuId, @Param("skuNum") Integer skuNum,@Param("wareId") Long wareId);

    Long getStockBySkuId(Long item);
}
