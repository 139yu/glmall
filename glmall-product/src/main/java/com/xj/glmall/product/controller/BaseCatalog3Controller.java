package com.xj.glmall.product.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.xj.glmall.product.entity.BaseCatalog3Entity;
import com.xj.glmall.product.service.BaseCatalog3Service;
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
@RequestMapping("product/basecatalog3")
public class BaseCatalog3Controller {
    @Autowired
    private BaseCatalog3Service baseCatalog3Service;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = baseCatalog3Service.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		BaseCatalog3Entity baseCatalog3 = baseCatalog3Service.getById(id);

        return R.ok().put("baseCatalog3", baseCatalog3);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody BaseCatalog3Entity baseCatalog3){
		baseCatalog3Service.save(baseCatalog3);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public R update(@RequestBody BaseCatalog3Entity baseCatalog3){
		baseCatalog3Service.updateById(baseCatalog3);

        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		baseCatalog3Service.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
