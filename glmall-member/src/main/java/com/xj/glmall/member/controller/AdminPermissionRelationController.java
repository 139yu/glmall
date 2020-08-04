package com.xj.glmall.member.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xj.glmall.member.entity.AdminPermissionRelationEntity;
import com.xj.glmall.member.service.AdminPermissionRelationService;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.common.utils.R;



/**
 * 后台用户和权限关系表(除角色中定义的权限以外的加减权限)
 *
 * @author yu
 * @email yu
 * @date 2020-06-24 20:01:37
 */
@RestController
@RequestMapping("member/adminpermissionrelation")
public class AdminPermissionRelationController {
    @Autowired
    private AdminPermissionRelationService adminPermissionRelationService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = adminPermissionRelationService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		AdminPermissionRelationEntity adminPermissionRelation = adminPermissionRelationService.getById(id);

        return R.ok().put("adminPermissionRelation", adminPermissionRelation);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody AdminPermissionRelationEntity adminPermissionRelation){
		adminPermissionRelationService.save(adminPermissionRelation);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody AdminPermissionRelationEntity adminPermissionRelation){
		adminPermissionRelationService.updateById(adminPermissionRelation);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		adminPermissionRelationService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
