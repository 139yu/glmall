package com.xj.glmall.coupon.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xj.glmall.coupon.entity.CouponProductRelationEntity;
import com.xj.glmall.coupon.service.CouponProductRelationService;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.common.utils.R;



/**
 * 优惠券和产品的关系表
 *
 * @author yu
 * @email ${email}
 * @date 2020-06-23 23:32:29
 */
@RestController
@RequestMapping("coupon/couponproductrelation")
public class CouponProductRelationController {
    @Autowired
    private CouponProductRelationService couponProductRelationService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = couponProductRelationService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		CouponProductRelationEntity couponProductRelation = couponProductRelationService.getById(id);

        return R.ok().put("couponProductRelation", couponProductRelation);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody CouponProductRelationEntity couponProductRelation){
		couponProductRelationService.save(couponProductRelation);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody CouponProductRelationEntity couponProductRelation){
		couponProductRelationService.updateById(couponProductRelation);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		couponProductRelationService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
