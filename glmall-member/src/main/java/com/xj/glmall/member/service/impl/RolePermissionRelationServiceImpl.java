package com.xj.glmall.member.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.common.utils.Query;

import com.xj.glmall.member.dao.RolePermissionRelationDao;
import com.xj.glmall.member.entity.RolePermissionRelationEntity;
import com.xj.glmall.member.service.RolePermissionRelationService;


@Service("rolePermissionRelationService")
public class RolePermissionRelationServiceImpl extends ServiceImpl<RolePermissionRelationDao, RolePermissionRelationEntity> implements RolePermissionRelationService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<RolePermissionRelationEntity> page = this.page(
                new Query<RolePermissionRelationEntity>().getPage(params),
                new QueryWrapper<RolePermissionRelationEntity>()
        );

        return new PageUtils(page);
    }

}