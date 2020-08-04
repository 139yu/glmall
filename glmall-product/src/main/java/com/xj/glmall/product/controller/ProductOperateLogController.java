package com.xj.glmall.product.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.xj.glmall.product.entity.ProductOperateLogEntity;
import com.xj.glmall.product.service.ProductOperateLogService;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.common.utils.R;



/**
 * 
 *
 * @author yu
 * @email ${email}
 * @date 2020-07-02 22:57:13
 */
@RestController
@RequestMapping("product/productoperatelog")
public class ProductOperateLogController {
    @Autowired
    private ProductOperateLogService productOperateLogService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = productOperateLogService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		ProductOperateLogEntity productOperateLog = productOperateLogService.getById(id);

        return R.ok().put("productOperateLog", productOperateLog);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody ProductOperateLogEntity productOperateLog){
		productOperateLogService.save(productOperateLog);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public R update(@RequestBody ProductOperateLogEntity productOperateLog){
		productOperateLogService.updateById(productOperateLog);

        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		productOperateLogService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
