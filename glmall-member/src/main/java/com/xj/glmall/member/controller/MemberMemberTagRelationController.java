package com.xj.glmall.member.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xj.glmall.member.entity.MemberMemberTagRelationEntity;
import com.xj.glmall.member.service.MemberMemberTagRelationService;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.common.utils.R;



/**
 * 用户和标签关系表
 *
 * @author yu
 * @email yu
 * @date 2020-06-24 20:01:36
 */
@RestController
@RequestMapping("member/membermembertagrelation")
public class MemberMemberTagRelationController {
    @Autowired
    private MemberMemberTagRelationService memberMemberTagRelationService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = memberMemberTagRelationService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		MemberMemberTagRelationEntity memberMemberTagRelation = memberMemberTagRelationService.getById(id);

        return R.ok().put("memberMemberTagRelation", memberMemberTagRelation);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody MemberMemberTagRelationEntity memberMemberTagRelation){
		memberMemberTagRelationService.save(memberMemberTagRelation);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody MemberMemberTagRelationEntity memberMemberTagRelation){
		memberMemberTagRelationService.updateById(memberMemberTagRelation);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		memberMemberTagRelationService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
