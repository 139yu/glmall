package com.xj.glmall.product.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.xj.glmall.product.entity.ProductAttrValueEntity;
import com.xj.glmall.product.service.ProductAttrValueService;
import com.xj.glmall.product.vo.AttrRespVo;
import com.xj.glmall.product.vo.AttrVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.xj.glmall.product.entity.AttrEntity;
import com.xj.glmall.product.service.AttrService;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.common.utils.R;



/**
 * 商品属性
 *
 * @author yu
 * @email ${email}
 * @date 2020-07-28 22:29:04
 */
@RestController
@RequestMapping("product/attr")
public class AttrController {
    @Autowired
    private AttrService attrService;

    @Autowired
    private ProductAttrValueService productAttrValueService;

    /**
     * 修改商品规格
     */
    @PostMapping("/update/{spuId}")
    public R updateProductAttrValueBySkuId(@PathVariable("spuId") Long spuId,@RequestBody List<ProductAttrValueEntity> productAttrValueEntities) {
        productAttrValueService.updateProductAttrValueBySpuId(spuId,productAttrValueEntities);
        return R.ok();
    }

    /**
     * 获取spu规格
     */
    @GetMapping("/base/listforspu/{spuId}")
    public R baseAttrListForSpuId(@PathVariable("spuId") Long spuId) {
        List<ProductAttrValueEntity> data = productAttrValueService.baseAttrListForSpuId(spuId);
        return R.ok().put("data",data);
    }

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = attrService.queryPage(params);

        return R.ok().put("page", page);
    }

    //@GetMapping("/{attrType}/list/{catalogId}")
    public R list(@RequestParam Map<String, Object> params,@PathVariable("catalogId") Long catalogId,@PathVariable("attrType") String type){
        PageUtils page = attrService.queryPage(params,catalogId,type);

        return R.ok().put("page", page);
    }

    @GetMapping("/{attrType}/list/{catalogId}")
    public R baseAttrList(@RequestParam Map<String,Object> params,@PathVariable("catalogId") Long catalogId,@PathVariable("attrType") String type){
        PageUtils page = attrService.getBaseAttrList(params,catalogId,type);
        return R.ok().put("page",page);
    }
    /**
     * 信息
     */
    @GetMapping("/info/{attrId}")
    public R info(@PathVariable("attrId") Long attrId){
		//AttrEntity attr = attrService.getById(attrId);
        AttrRespVo attr = attrService.getAttrInfo(attrId);
        return R.ok().put("attr", attr);
    }
/*
    @GetMapping("/sale/list/{catalogId}")
    public R saleList(@RequestParam Map<String, Object> map, @PathVariable("catalogId") Long catalogId) {

    }*/

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody AttrVo attrVo){
		attrService.saveAttr(attrVo);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public R update(@RequestBody AttrVo attrVo){
		attrService.updateAttr(attrVo);

        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public R delete(@RequestBody Long[] attrIds){
		attrService.removeByIds(Arrays.asList(attrIds));

        return R.ok();
    }

}
