package com.xj.glmall.product.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.xj.glmall.product.entity.ProductSaleAttrValueEntity;
import com.xj.glmall.product.service.ProductSaleAttrValueService;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.common.utils.R;



/**
 * spu销售属性值
 *
 * @author yu
 * @email ${email}
 * @date 2020-07-02 22:57:13
 */
@RestController
@RequestMapping("product/productsaleattrvalue")
public class ProductSaleAttrValueController {
    @Autowired
    private ProductSaleAttrValueService productSaleAttrValueService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = productSaleAttrValueService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		ProductSaleAttrValueEntity productSaleAttrValue = productSaleAttrValueService.getById(id);

        return R.ok().put("productSaleAttrValue", productSaleAttrValue);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody ProductSaleAttrValueEntity productSaleAttrValue){
		productSaleAttrValueService.save(productSaleAttrValue);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public R update(@RequestBody ProductSaleAttrValueEntity productSaleAttrValue){
		productSaleAttrValueService.updateById(productSaleAttrValue);

        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		productSaleAttrValueService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
