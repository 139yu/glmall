package com.xj.glmall.product.dao;

import com.xj.glmall.product.entity.SkuSaleAttrValueEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xj.glmall.product.vo.SkuItemSaleAttrVo;
import com.xj.glmall.product.vo.SkuItemVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * sku销售属性&值
 * 
 * @author yu
 * @email ${email}
 * @date 2020-07-28 22:29:04
 */
@Mapper
public interface SkuSaleAttrValueDao extends BaseMapper<SkuSaleAttrValueEntity> {

    List<SkuItemSaleAttrVo> listSkuSaleAttrValueBySpuId(@Param("spuId") Long spuId);

    List<String> listStringSaleAttr(@Param("skuId") Long skuId);
}
