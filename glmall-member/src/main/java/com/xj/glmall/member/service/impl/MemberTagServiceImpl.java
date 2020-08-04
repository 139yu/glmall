package com.xj.glmall.member.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.common.utils.Query;

import com.xj.glmall.member.dao.MemberTagDao;
import com.xj.glmall.member.entity.MemberTagEntity;
import com.xj.glmall.member.service.MemberTagService;


@Service("memberTagService")
public class MemberTagServiceImpl extends ServiceImpl<MemberTagDao, MemberTagEntity> implements MemberTagService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MemberTagEntity> page = this.page(
                new Query<MemberTagEntity>().getPage(params),
                new QueryWrapper<MemberTagEntity>()
        );

        return new PageUtils(page);
    }

}