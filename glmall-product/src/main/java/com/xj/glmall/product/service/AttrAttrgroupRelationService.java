package com.xj.glmall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.product.entity.AttrAttrgroupRelationEntity;
import com.xj.glmall.product.vo.AttrGroupRelationVo;

import java.util.List;
import java.util.Map;

/**
 * 属性&属性分组关联
 *
 * @author yu
 * @email ${email}
 * @date 2020-07-28 22:29:04
 */
public interface AttrAttrgroupRelationService extends IService<AttrAttrgroupRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);


    void saveBatch(List<AttrGroupRelationVo> vos);
}

