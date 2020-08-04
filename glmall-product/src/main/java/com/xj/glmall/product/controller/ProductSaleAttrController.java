package com.xj.glmall.product.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.xj.glmall.product.entity.ProductSaleAttrEntity;
import com.xj.glmall.product.service.ProductSaleAttrService;
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
@RequestMapping("product/productsaleattr")
public class ProductSaleAttrController {
    @Autowired
    private ProductSaleAttrService productSaleAttrService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = productSaleAttrService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		ProductSaleAttrEntity productSaleAttr = productSaleAttrService.getById(id);

        return R.ok().put("productSaleAttr", productSaleAttr);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody ProductSaleAttrEntity productSaleAttr){
		productSaleAttrService.save(productSaleAttr);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public R update(@RequestBody ProductSaleAttrEntity productSaleAttr){
		productSaleAttrService.updateById(productSaleAttr);

        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		productSaleAttrService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
