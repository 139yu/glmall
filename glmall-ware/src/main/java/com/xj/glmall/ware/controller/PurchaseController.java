package com.xj.glmall.ware.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.xj.glmall.ware.vo.MergeVo;
import com.xj.glmall.ware.vo.PurchaseDoneVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.xj.glmall.ware.entity.PurchaseEntity;
import com.xj.glmall.ware.service.PurchaseService;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.common.utils.R;



/**
 * 采购信息
 *
 * @author yu
 * @email ${email}
 * @date 2020-09-14 22:29:45
 */
@RestController
@RequestMapping("ware/purchase")
public class PurchaseController {
    @Autowired
    private PurchaseService purchaseService;
    /**
     * 合并采购单
     */
    @PostMapping("/merge")
    public R merge(@RequestBody MergeVo mergeVo) {
        purchaseService.merge(mergeVo);
        return R.ok();
    }

    /**
     * 完成采购
     * @param
     * @return
     */
    @PostMapping("/done")
    public R finishPurchase(@RequestBody PurchaseDoneVo vo){
        purchaseService.finishPurchase(vo);
        return R.ok();
    }
    @PostMapping("/received")
    public R receivedPurchase(@RequestBody List<Long> ids){
        purchaseService.receivedPurchase(ids);
        return R.ok();
    }
    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = purchaseService.queryPage(params);

        return R.ok().put("page", page);
    }
    ///ware/purchase/unreceive/list
    @GetMapping("/unreceive/list")
    public R unreceiveList(@RequestParam Map<String, Object> params){
        PageUtils page = purchaseService.queryUnReceiveListPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		PurchaseEntity purchase = purchaseService.getById(id);

        return R.ok().put("purchase", purchase);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody PurchaseEntity purchase){
        purchase.setCreateTime(new Date());
        purchase.setUpdateTime(new Date());
		purchaseService.save(purchase);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public R update(@RequestBody PurchaseEntity purchase){
		purchaseService.updateById(purchase);

        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		purchaseService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
