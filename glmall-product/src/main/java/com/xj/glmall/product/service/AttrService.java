package com.xj.glmall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.product.entity.AttrEntity;
import com.xj.glmall.product.vo.AttrRespVo;
import com.xj.glmall.product.vo.AttrVo;

import java.util.List;
import java.util.Map;

/**
 * 商品属性
 *
 * @author yu
 * @email ${email}
 * @date 2020-07-28 22:29:04
 */
public interface AttrService extends IService<AttrEntity> {

    PageUtils queryPage(Map<String, Object> params);
    PageUtils queryPage(Map<String,Object> params,Long catelogId,String type);

    @Override
    boolean save(AttrEntity entity);

    boolean saveAttr(AttrVo attrVo);

    PageUtils getBaseAttrList(Map<String, Object> params, Long catelogId,String type);

    AttrRespVo getAttrInfo(Long attrId);

    boolean updateAttr(AttrVo attrVo);

    List<AttrEntity> getRelations(Long attrgroupId);

    PageUtils getAttrNoRelation(Map<String, Object> params, Long attrgroupId);
}

