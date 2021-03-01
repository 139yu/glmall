package com.xj.glmall.product.dao;

import com.xj.glmall.product.entity.AttrAttrgroupRelationEntity;
import com.xj.glmall.product.entity.AttrEntity;
import com.xj.glmall.product.entity.AttrGroupEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xj.glmall.product.vo.SpuItemBaseAttrVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 属性分组
 *
 * @author yu
 * @email ${email}
 * @date 2020-07-28 22:29:04
 */
@Mapper
public interface AttrGroupDao extends BaseMapper<AttrGroupEntity> {

    List<AttrEntity> selectAttrByAttrgroupId(@Param("attrgroupId") String attrgroupId);

    void deleteBatchRelation(@Param("relationEntities") List<AttrAttrgroupRelationEntity> relationEntities);

    List<SpuItemBaseAttrVo> listAttrGroupWithAttrsBySpuIdAndCatalogId(@Param("spuId") Long spuId, @Param("catalogId") Long catalogId);
}
