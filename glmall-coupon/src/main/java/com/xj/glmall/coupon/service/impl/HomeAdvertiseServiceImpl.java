package com.xj.glmall.coupon.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.common.utils.Query;

import com.xj.glmall.coupon.dao.HomeAdvertiseDao;
import com.xj.glmall.coupon.entity.HomeAdvertiseEntity;
import com.xj.glmall.coupon.service.HomeAdvertiseService;


@Service("homeAdvertiseService")
public class HomeAdvertiseServiceImpl extends ServiceImpl<HomeAdvertiseDao, HomeAdvertiseEntity> implements HomeAdvertiseService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<HomeAdvertiseEntity> page = this.page(
                new Query<HomeAdvertiseEntity>().getPage(params),
                new QueryWrapper<HomeAdvertiseEntity>()
        );

        return new PageUtils(page);
    }

}