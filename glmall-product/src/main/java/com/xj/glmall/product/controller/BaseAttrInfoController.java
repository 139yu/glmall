package com.xj.glmall.product.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.xj.glmall.product.entity.BaseAttrInfoEntity;
import com.xj.glmall.product.service.BaseAttrInfoService;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.common.utils.R;



/**
 * 属性表
 *
 * @author yu
 * @email ${email}
 * @date 2020-07-02 22:57:14
 */
@RestController
@RequestMapping("product/baseattrinfo")
public class BaseAttrInfoController {
    @Autowired
    private BaseAttrInfoService baseAttrInfoService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = baseAttrInfoService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		BaseAttrInfoEntity baseAttrInfo = baseAttrInfoService.getById(id);

        return R.ok().put("baseAttrInfo", baseAttrInfo);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody BaseAttrInfoEntity baseAttrInfo){
		baseAttrInfoService.save(baseAttrInfo);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public R update(@RequestBody BaseAttrInfoEntity baseAttrInfo){
		baseAttrInfoService.updateById(baseAttrInfo);

        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		baseAttrInfoService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
