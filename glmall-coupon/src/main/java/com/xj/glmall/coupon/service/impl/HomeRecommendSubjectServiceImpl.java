package com.xj.glmall.coupon.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.common.utils.Query;

import com.xj.glmall.coupon.dao.HomeRecommendSubjectDao;
import com.xj.glmall.coupon.entity.HomeRecommendSubjectEntity;
import com.xj.glmall.coupon.service.HomeRecommendSubjectService;


@Service("homeRecommendSubjectService")
public class HomeRecommendSubjectServiceImpl extends ServiceImpl<HomeRecommendSubjectDao, HomeRecommendSubjectEntity> implements HomeRecommendSubjectService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<HomeRecommendSubjectEntity> page = this.page(
                new Query<HomeRecommendSubjectEntity>().getPage(params),
                new QueryWrapper<HomeRecommendSubjectEntity>()
        );

        return new PageUtils(page);
    }

}