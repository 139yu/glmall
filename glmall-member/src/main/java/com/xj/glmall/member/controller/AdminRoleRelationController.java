package com.xj.glmall.member.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xj.glmall.member.entity.AdminRoleRelationEntity;
import com.xj.glmall.member.service.AdminRoleRelationService;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.common.utils.R;



/**
 * 后台用户和角色关系表
 *
 * @author yu
 * @email yu
 * @date 2020-06-24 20:01:37
 */
@RestController
@RequestMapping("member/adminrolerelation")
public class AdminRoleRelationController {
    @Autowired
    private AdminRoleRelationService adminRoleRelationService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = adminRoleRelationService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		AdminRoleRelationEntity adminRoleRelation = adminRoleRelationService.getById(id);

        return R.ok().put("adminRoleRelation", adminRoleRelation);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody AdminRoleRelationEntity adminRoleRelation){
		adminRoleRelationService.save(adminRoleRelation);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody AdminRoleRelationEntity adminRoleRelation){
		adminRoleRelationService.updateById(adminRoleRelation);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		adminRoleRelationService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
