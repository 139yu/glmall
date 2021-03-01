package com.xj.glmall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.product.entity.ProductAttrValueEntity;
import com.xj.glmall.product.vo.BaseAttrs;

import java.util.List;
import java.util.Map;

/**
 * spu属性值
 *
 * @author yu
 * @email ${email}
 * @date 2020-07-28 22:29:04
 */
public interface ProductAttrValueService extends IService<ProductAttrValueEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveAttrs(Long spuId, List<BaseAttrs> baseAttrs);

    List<ProductAttrValueEntity> baseAttrListForSpuId(Long spuId);

    void updateProductAttrValueBySpuId(Long skuId, List<ProductAttrValueEntity> productAttrValueEntities);

    List<ProductAttrValueEntity> listBySpuIdAndGroupId(Long spuId,Long groupId);
}

