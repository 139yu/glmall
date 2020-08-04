package com.xj.glmall.coupon.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xj.glmall.coupon.entity.HomeRecommendProductEntity;
import com.xj.glmall.coupon.service.HomeRecommendProductService;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.common.utils.R;



/**
 * 人气推荐商品表
 *
 * @author yu
 * @email ${email}
 * @date 2020-06-23 23:32:29
 */
@RestController
@RequestMapping("coupon/homerecommendproduct")
public class HomeRecommendProductController {
    @Autowired
    private HomeRecommendProductService homeRecommendProductService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = homeRecommendProductService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		HomeRecommendProductEntity homeRecommendProduct = homeRecommendProductService.getById(id);

        return R.ok().put("homeRecommendProduct", homeRecommendProduct);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody HomeRecommendProductEntity homeRecommendProduct){
		homeRecommendProductService.save(homeRecommendProduct);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody HomeRecommendProductEntity homeRecommendProduct){
		homeRecommendProductService.updateById(homeRecommendProduct);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		homeRecommendProductService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
