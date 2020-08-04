package com.xj.glmall.product.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.common.utils.Query;

import com.xj.glmall.product.dao.BaseCatalog1Dao;
import com.xj.glmall.product.entity.BaseCatalog1Entity;
import com.xj.glmall.product.service.BaseCatalog1Service;


@Service("baseCatalog1Service")
public class BaseCatalog1ServiceImpl extends ServiceImpl<BaseCatalog1Dao, BaseCatalog1Entity> implements BaseCatalog1Service {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<BaseCatalog1Entity> page = this.page(
                new Query<BaseCatalog1Entity>().getPage(params),
                new QueryWrapper<BaseCatalog1Entity>()
        );

        return new PageUtils(page);
    }

}