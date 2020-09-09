package com.xj.glmall.product.controller;

import java.util.Arrays;
import java.util.Map;

import com.xj.glmall.common.valid.AddGroup;
import com.xj.glmall.common.valid.UpdateGroup;
import com.xj.glmall.common.valid.UpdateStatusGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.xj.glmall.product.entity.BrandEntity;
import com.xj.glmall.product.service.BrandService;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.common.utils.R;

import javax.validation.Valid;


/**
 * 品牌
 *
 * @author yu
 * @email ${email}
 * @date 2020-07-28 22:29:04
 */
@RestController
@RequestMapping("product/brand")
public class BrandController {
    @Autowired
    private BrandService brandService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = brandService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{brandId}")
    public R info(@PathVariable("brandId") Long brandId){
		BrandEntity brand = brandService.getById(brandId);

        return R.ok().put("brand", brand);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@Validated(AddGroup.class) @RequestBody BrandEntity brand){
		brandService.save(brand);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public R update(@Validated(UpdateGroup.class) @RequestBody BrandEntity brand){
        brandService.updateCascade(brand);
        return R.ok();
    }

    @PostMapping("/update/status")
    //@RequiresPermissions("product:brand:update")
    public R updateStatus(@Validated(UpdateStatusGroup.class) @RequestBody BrandEntity brand){
        brandService.updateById(brand);

        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public R delete(@RequestBody Long[] brandIds){
		brandService.removeByIds(Arrays.asList(brandIds));

        return R.ok();
    }

}
