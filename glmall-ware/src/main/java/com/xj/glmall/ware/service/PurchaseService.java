package com.xj.glmall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.ware.entity.PurchaseEntity;
import com.xj.glmall.ware.vo.MergeVo;
import com.xj.glmall.ware.vo.PurchaseDoneVo;

import java.util.List;
import java.util.Map;

/**
 * 采购信息
 *
 * @author yu
 * @email ${email}
 * @date 2020-09-14 22:29:45
 */
public interface PurchaseService extends IService<PurchaseEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryUnReceiveListPage(Map<String, Object> params);

    void merge(MergeVo mergeVo);

    void receivedPurchase(List<Long> ids);

    void finishPurchase(PurchaseDoneVo vo);
}

