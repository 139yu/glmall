package com.xj.glmall.product.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.xj.glmall.product.entity.BaseSaleAttrEntity;
import com.xj.glmall.product.service.BaseSaleAttrService;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.common.utils.R;



/**
 * 
 *
 * @author yu
 * @email ${email}
 * @date 2020-07-02 22:57:14
 */
@RestController
@RequestMapping("product/basesaleattr")
public class BaseSaleAttrController {
    @Autowired
    private BaseSaleAttrService baseSaleAttrService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = baseSaleAttrService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		BaseSaleAttrEntity baseSaleAttr = baseSaleAttrService.getById(id);

        return R.ok().put("baseSaleAttr", baseSaleAttr);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody BaseSaleAttrEntity baseSaleAttr){
		baseSaleAttrService.save(baseSaleAttr);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public R update(@RequestBody BaseSaleAttrEntity baseSaleAttr){
		baseSaleAttrService.updateById(baseSaleAttr);

        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		baseSaleAttrService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
