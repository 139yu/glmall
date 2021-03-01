package com.xj.glmall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.product.entity.AttrEntity;
import com.xj.glmall.product.entity.AttrGroupEntity;
import com.xj.glmall.product.vo.AttrGroupRelationVo;
import com.xj.glmall.product.vo.AttrGroupVo;
import com.xj.glmall.product.vo.SpuItemBaseAttrVo;

import java.util.List;
import java.util.Map;

/**
 * 属性分组
 *
 * @author yu
 * @email ${email}
 * @date 2020-07-28 22:29:04
 */
public interface AttrGroupService extends IService<AttrGroupEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryPage(Map<String,Object> params,Long categoryId);

    void deleteRelation(AttrGroupRelationVo[] attrGroupRelationVos);

    List<AttrGroupVo> getAttrGroupWithAttr(Long catalogId);

    List<AttrGroupEntity> listByCategoryId(Long catalogId);

    List<SpuItemBaseAttrVo> listAttrGroupWithAttrsBySpuIdAndCatalogId(Long spuId,Long catalogId);
}

