package com.xj.glmall.product.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.xj.glmall.product.entity.BaseAttrValueEntity;
import com.xj.glmall.product.service.BaseAttrValueService;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.common.utils.R;



/**
 * 属性值表
 *
 * @author yu
 * @email ${email}
 * @date 2020-07-02 22:57:14
 */
@RestController
@RequestMapping("product/baseattrvalue")
public class BaseAttrValueController {
    @Autowired
    private BaseAttrValueService baseAttrValueService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = baseAttrValueService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		BaseAttrValueEntity baseAttrValue = baseAttrValueService.getById(id);

        return R.ok().put("baseAttrValue", baseAttrValue);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody BaseAttrValueEntity baseAttrValue){
		baseAttrValueService.save(baseAttrValue);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public R update(@RequestBody BaseAttrValueEntity baseAttrValue){
		baseAttrValueService.updateById(baseAttrValue);

        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		baseAttrValueService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
