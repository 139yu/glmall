package com.xj.glmall.product.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.xj.glmall.product.entity.ProductImageEntity;
import com.xj.glmall.product.service.ProductImageService;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.common.utils.R;



/**
 * 商品图片表
 *
 * @author yu
 * @email ${email}
 * @date 2020-07-02 22:57:13
 */
@RestController
@RequestMapping("product/productimage")
public class ProductImageController {
    @Autowired
    private ProductImageService productImageService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = productImageService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		ProductImageEntity productImage = productImageService.getById(id);

        return R.ok().put("productImage", productImage);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody ProductImageEntity productImage){
		productImageService.save(productImage);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public R update(@RequestBody ProductImageEntity productImage){
		productImageService.updateById(productImage);

        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		productImageService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
