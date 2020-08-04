package com.xj.glmall.member.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xj.glmall.member.entity.RolePermissionRelationEntity;
import com.xj.glmall.member.service.RolePermissionRelationService;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.common.utils.R;



/**
 * 后台用户角色和权限关系表
 *
 * @author yu
 * @email yu
 * @date 2020-06-24 20:01:37
 */
@RestController
@RequestMapping("member/rolepermissionrelation")
public class RolePermissionRelationController {
    @Autowired
    private RolePermissionRelationService rolePermissionRelationService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = rolePermissionRelationService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		RolePermissionRelationEntity rolePermissionRelation = rolePermissionRelationService.getById(id);

        return R.ok().put("rolePermissionRelation", rolePermissionRelation);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody RolePermissionRelationEntity rolePermissionRelation){
		rolePermissionRelationService.save(rolePermissionRelation);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody RolePermissionRelationEntity rolePermissionRelation){
		rolePermissionRelationService.updateById(rolePermissionRelation);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		rolePermissionRelationService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
