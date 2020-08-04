package com.xj.glmall.product.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.xj.glmall.product.entity.SkuImageEntity;
import com.xj.glmall.product.service.SkuImageService;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.common.utils.R;



/**
 * 库存单元图片表
 *
 * @author yu
 * @email ${email}
 * @date 2020-07-02 22:57:13
 */
@RestController
@RequestMapping("product/skuimage")
public class SkuImageController {
    @Autowired
    private SkuImageService skuImageService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = skuImageService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		SkuImageEntity skuImage = skuImageService.getById(id);

        return R.ok().put("skuImage", skuImage);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody SkuImageEntity skuImage){
		skuImageService.save(skuImage);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public R update(@RequestBody SkuImageEntity skuImage){
		skuImageService.updateById(skuImage);

        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		skuImageService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
