package com.xj.glmall.coupon.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.common.utils.Query;

import com.xj.glmall.coupon.dao.HomeBrandDao;
import com.xj.glmall.coupon.entity.HomeBrandEntity;
import com.xj.glmall.coupon.service.HomeBrandService;


@Service("homeBrandService")
public class HomeBrandServiceImpl extends ServiceImpl<HomeBrandDao, HomeBrandEntity> implements HomeBrandService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<HomeBrandEntity> page = this.page(
                new Query<HomeBrandEntity>().getPage(params),
                new QueryWrapper<HomeBrandEntity>()
        );

        return new PageUtils(page);
    }

}