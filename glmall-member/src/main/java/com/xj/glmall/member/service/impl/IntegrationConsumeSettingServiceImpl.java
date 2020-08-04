package com.xj.glmall.member.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.common.utils.Query;

import com.xj.glmall.member.dao.IntegrationConsumeSettingDao;
import com.xj.glmall.member.entity.IntegrationConsumeSettingEntity;
import com.xj.glmall.member.service.IntegrationConsumeSettingService;


@Service("integrationConsumeSettingService")
public class IntegrationConsumeSettingServiceImpl extends ServiceImpl<IntegrationConsumeSettingDao, IntegrationConsumeSettingEntity> implements IntegrationConsumeSettingService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<IntegrationConsumeSettingEntity> page = this.page(
                new Query<IntegrationConsumeSettingEntity>().getPage(params),
                new QueryWrapper<IntegrationConsumeSettingEntity>()
        );

        return new PageUtils(page);
    }

}