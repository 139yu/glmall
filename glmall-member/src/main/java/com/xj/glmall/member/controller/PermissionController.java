package com.xj.glmall.member.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xj.glmall.member.entity.PermissionEntity;
import com.xj.glmall.member.service.PermissionService;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.common.utils.R;



/**
 * 后台用户权限表
 *
 * @author yu
 * @email yu
 * @date 2020-06-24 20:01:37
 */
@RestController
@RequestMapping("member/permission")
public class PermissionController {
    @Autowired
    private PermissionService permissionService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = permissionService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		PermissionEntity permission = permissionService.getById(id);

        return R.ok().put("permission", permission);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody PermissionEntity permission){
		permissionService.save(permission);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody PermissionEntity permission){
		permissionService.updateById(permission);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		permissionService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
