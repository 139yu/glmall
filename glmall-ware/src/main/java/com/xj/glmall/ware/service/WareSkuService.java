package com.xj.glmall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.ware.entity.WareSkuEntity;
import com.xj.glmall.ware.vo.SkuHasStockVo;

import java.util.List;
import java.util.Map;

/**
 * 商品库存
 *
 * @author yu
 * @email ${email}
 * @date 2020-09-14 22:29:45
 */
public interface WareSkuService extends IService<WareSkuEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void addStock(Long skuId, Long skuNum, Long wareId);

    void saveWareSku(WareSkuEntity wareSku);

    Integer getSkuStock(Long skuId);

    List<SkuHasStockVo> getSkuHasStock(List<Long> skuIds);
}

