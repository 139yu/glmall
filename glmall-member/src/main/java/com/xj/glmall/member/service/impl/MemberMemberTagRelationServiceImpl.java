package com.xj.glmall.member.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.common.utils.Query;

import com.xj.glmall.member.dao.MemberMemberTagRelationDao;
import com.xj.glmall.member.entity.MemberMemberTagRelationEntity;
import com.xj.glmall.member.service.MemberMemberTagRelationService;


@Service("memberMemberTagRelationService")
public class MemberMemberTagRelationServiceImpl extends ServiceImpl<MemberMemberTagRelationDao, MemberMemberTagRelationEntity> implements MemberMemberTagRelationService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MemberMemberTagRelationEntity> page = this.page(
                new Query<MemberMemberTagRelationEntity>().getPage(params),
                new QueryWrapper<MemberMemberTagRelationEntity>()
        );

        return new PageUtils(page);
    }

}