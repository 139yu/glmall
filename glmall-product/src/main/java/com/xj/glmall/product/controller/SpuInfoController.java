package com.xj.glmall.product.controller;

import java.util.Arrays;
import java.util.Map;

import com.xj.glmall.product.vo.SpuSaveVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.xj.glmall.product.entity.SpuInfoEntity;
import com.xj.glmall.product.service.SpuInfoService;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.common.utils.R;



/**
 * spu信息
 *
 * @author yu
 * @email ${email}
 * @date 2020-07-28 22:29:04
 */
@RestController
@RequestMapping("product/spuinfo")
public class SpuInfoController {
    @Autowired
    private SpuInfoService spuInfoService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = spuInfoService.queryPageByCondition(params);

        return R.ok().put("page", page);
    }

    /**
     * 上架
     * @param spuId
     * @return
     */
    @PostMapping("/{spuId}/up")
    public R up(@PathVariable("spuId") Long spuId){
        spuInfoService.up(spuId);
        return R.ok();
    }

    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		SpuInfoEntity spuInfo = spuInfoService.getById(id);

        return R.ok().put("spuInfo", spuInfo);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody SpuSaveVo spuInfo){
		spuInfoService.saveSpuInfo(spuInfo);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public R update(@RequestBody SpuInfoEntity spuInfo){
		spuInfoService.updateById(spuInfo);

        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		spuInfoService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
