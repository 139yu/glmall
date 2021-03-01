package com.xj.glmall.product.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.xj.glmall.product.entity.AttrAttrgroupRelationEntity;
import com.xj.glmall.product.entity.AttrEntity;
import com.xj.glmall.product.service.AttrAttrgroupRelationService;
import com.xj.glmall.product.service.AttrService;
import com.xj.glmall.product.service.CategoryService;
import com.xj.glmall.product.vo.AttrGroupRelationVo;
import com.xj.glmall.product.vo.AttrGroupVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.xj.glmall.product.entity.AttrGroupEntity;
import com.xj.glmall.product.service.AttrGroupService;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.common.utils.R;



/**
 * 属性分组
 *
 * @author yu
 * @email ${email}
 * @date 2020-07-28 22:29:04
 */
@RestController
@RequestMapping("product/attrgroup/")
public class AttrGroupController {
    @Autowired
    private AttrGroupService attrGroupService;

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private AttrService attrService;
    @Autowired
    private AttrAttrgroupRelationService relationService;

    @PostMapping("attr/relation")
    public R setRelation(@RequestBody List<AttrGroupRelationVo> vos) {
        relationService.saveBatch(vos);
        return R.ok();
    }
    @GetMapping("/{catalogId}/withattr")
    public R getAttrGroupWithAttr(@PathVariable("catalogId") Long catalogId){
        List<AttrGroupVo> attrGroupVoList = attrGroupService.getAttrGroupWithAttr(catalogId);
        return R.ok().put("data",attrGroupVoList);
    }


    @GetMapping("/{attrgroupId}/noattr/relation")
    public R getAttrNoRelation(@RequestParam Map<String,Object> params,@PathVariable("attrgroupId") Long attrgroupId) {
        PageUtils page = attrService.getAttrNoRelation(params,attrgroupId);
        return R.ok().put("page",page);
    }
    /**
     * 列表
     */
    @GetMapping("/list/{catId}")
    public R list(@RequestParam Map<String, Object> params,@PathVariable("catId") Long catId){
        PageUtils page = attrGroupService.queryPage(params,catId);

        return R.ok().put("page", page);
    }

    @GetMapping("/{attrgroudIp}/attr/relation")
    public R getRelations(@PathVariable("attrgroudIp") Long attrgroupId){
        List<AttrEntity> list = attrService.getRelations(attrgroupId);
        return R.ok().put("data",list);
    }
    @PostMapping("/attr/relation/delete")
    public R deleteRelation(@RequestBody AttrGroupRelationVo[] attrGroupRelationVos){
        attrGroupService.deleteRelation(attrGroupRelationVos);
        return R.ok();
    }
    /**
     * 信息
     */
    @GetMapping("/info/{attrGroupId}")
    public R info(@PathVariable("attrGroupId") Long attrGroupId){
		AttrGroupEntity attrGroup = attrGroupService.getById(attrGroupId);
        Long[] catalogPath = categoryService.findCatalogPath(attrGroup.getCatalogId());
        attrGroup.setCatalogPath(catalogPath);
        return R.ok().put("attrGroup", attrGroup);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody AttrGroupEntity attrGroup){
		attrGroupService.save(attrGroup);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public R update(@RequestBody AttrGroupEntity attrGroup){
		attrGroupService.updateById(attrGroup);

        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public R delete(@RequestBody Long[] attrGroupIds){
		attrGroupService.removeByIds(Arrays.asList(attrGroupIds));

        return R.ok();
    }

}
