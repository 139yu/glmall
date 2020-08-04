package com.xj.glmall.product.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.xj.glmall.product.entity.BaseCatalog2Entity;
import com.xj.glmall.product.service.BaseCatalog2Service;
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
@RequestMapping("product/basecatalog2")
public class BaseCatalog2Controller {
    @Autowired
    private BaseCatalog2Service baseCatalog2Service;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = baseCatalog2Service.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Integer id){
		BaseCatalog2Entity baseCatalog2 = baseCatalog2Service.getById(id);

        return R.ok().put("baseCatalog2", baseCatalog2);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody BaseCatalog2Entity baseCatalog2){
		baseCatalog2Service.save(baseCatalog2);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public R update(@RequestBody BaseCatalog2Entity baseCatalog2){
		baseCatalog2Service.updateById(baseCatalog2);

        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public R delete(@RequestBody Integer[] ids){
		baseCatalog2Service.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
