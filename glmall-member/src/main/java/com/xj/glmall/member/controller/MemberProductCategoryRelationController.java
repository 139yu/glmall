package com.xj.glmall.member.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xj.glmall.member.entity.MemberProductCategoryRelationEntity;
import com.xj.glmall.member.service.MemberProductCategoryRelationService;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.common.utils.R;



/**
 * 会员与产品分类关系表（用户喜欢的分类）
 *
 * @author yu
 * @email yu
 * @date 2020-06-24 20:01:36
 */
@RestController
@RequestMapping("member/memberproductcategoryrelation")
public class MemberProductCategoryRelationController {
    @Autowired
    private MemberProductCategoryRelationService memberProductCategoryRelationService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = memberProductCategoryRelationService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		MemberProductCategoryRelationEntity memberProductCategoryRelation = memberProductCategoryRelationService.getById(id);

        return R.ok().put("memberProductCategoryRelation", memberProductCategoryRelation);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody MemberProductCategoryRelationEntity memberProductCategoryRelation){
		memberProductCategoryRelationService.save(memberProductCategoryRelation);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody MemberProductCategoryRelationEntity memberProductCategoryRelation){
		memberProductCategoryRelationService.updateById(memberProductCategoryRelation);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		memberProductCategoryRelationService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
