package com.xj.glmall.coupon.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.xj.glmall.coupon.entity.CouponSpuRelationEntity;
import com.xj.glmall.coupon.service.CouponSpuRelationService;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.common.utils.R;



/**
 * 优惠券与产品关联
 *
 * @author yu
 * @email ${email}
 * @date 2020-09-08 22:33:12
 */
@RestController
@RequestMapping("coupon/couponspurelation")
public class CouponSpuRelationController {
    @Autowired
    private CouponSpuRelationService couponSpuRelationService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = couponSpuRelationService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		CouponSpuRelationEntity couponSpuRelation = couponSpuRelationService.getById(id);

        return R.ok().put("couponSpuRelation", couponSpuRelation);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody CouponSpuRelationEntity couponSpuRelation){
		couponSpuRelationService.save(couponSpuRelation);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public R update(@RequestBody CouponSpuRelationEntity couponSpuRelation){
		couponSpuRelationService.updateById(couponSpuRelation);

        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		couponSpuRelationService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
