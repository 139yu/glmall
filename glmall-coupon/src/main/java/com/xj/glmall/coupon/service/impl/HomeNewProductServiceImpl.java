package com.xj.glmall.coupon.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.common.utils.Query;

import com.xj.glmall.coupon.dao.HomeNewProductDao;
import com.xj.glmall.coupon.entity.HomeNewProductEntity;
import com.xj.glmall.coupon.service.HomeNewProductService;


@Service("homeNewProductService")
public class HomeNewProductServiceImpl extends ServiceImpl<HomeNewProductDao, HomeNewProductEntity> implements HomeNewProductService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<HomeNewProductEntity> page = this.page(
                new Query<HomeNewProductEntity>().getPage(params),
                new QueryWrapper<HomeNewProductEntity>()
        );

        return new PageUtils(page);
    }

}