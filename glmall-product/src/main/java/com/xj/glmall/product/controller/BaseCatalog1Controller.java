package com.xj.glmall.product.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.xj.glmall.product.entity.BaseCatalog1Entity;
import com.xj.glmall.product.service.BaseCatalog1Service;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.common.utils.R;



/**
 * 一级分类表
 *
 * @author yu
 * @email ${email}
 * @date 2020-07-02 22:57:14
 */
@RestController
@RequestMapping("product/basecatalog1")
public class BaseCatalog1Controller {
    @Autowired
    private BaseCatalog1Service baseCatalog1Service;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = baseCatalog1Service.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Integer id){
		BaseCatalog1Entity baseCatalog1 = baseCatalog1Service.getById(id);

        return R.ok().put("baseCatalog1", baseCatalog1);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody BaseCatalog1Entity baseCatalog1){
		baseCatalog1Service.save(baseCatalog1);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public R update(@RequestBody BaseCatalog1Entity baseCatalog1){
		baseCatalog1Service.updateById(baseCatalog1);

        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public R delete(@RequestBody Integer[] ids){
		baseCatalog1Service.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
