package com.xj.glmall.member.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xj.glmall.member.entity.MemberRuleSettingEntity;
import com.xj.glmall.member.service.MemberRuleSettingService;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.common.utils.R;



/**
 * 会员积分成长规则表
 *
 * @author yu
 * @email yu
 * @date 2020-06-24 20:01:36
 */
@RestController
@RequestMapping("member/memberrulesetting")
public class MemberRuleSettingController {
    @Autowired
    private MemberRuleSettingService memberRuleSettingService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = memberRuleSettingService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		MemberRuleSettingEntity memberRuleSetting = memberRuleSettingService.getById(id);

        return R.ok().put("memberRuleSetting", memberRuleSetting);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody MemberRuleSettingEntity memberRuleSetting){
		memberRuleSettingService.save(memberRuleSetting);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody MemberRuleSettingEntity memberRuleSetting){
		memberRuleSettingService.updateById(memberRuleSetting);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		memberRuleSettingService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
