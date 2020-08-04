package com.xj.glmall.member.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.common.utils.Query;

import com.xj.glmall.member.dao.MemberTaskDao;
import com.xj.glmall.member.entity.MemberTaskEntity;
import com.xj.glmall.member.service.MemberTaskService;


@Service("memberTaskService")
public class MemberTaskServiceImpl extends ServiceImpl<MemberTaskDao, MemberTaskEntity> implements MemberTaskService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MemberTaskEntity> page = this.page(
                new Query<MemberTaskEntity>().getPage(params),
                new QueryWrapper<MemberTaskEntity>()
        );

        return new PageUtils(page);
    }

}