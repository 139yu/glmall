package com.xj.glmall.product.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.common.utils.Query;

import com.xj.glmall.product.dao.ProductOperateLogDao;
import com.xj.glmall.product.entity.ProductOperateLogEntity;
import com.xj.glmall.product.service.ProductOperateLogService;


@Service("productOperateLogService")
public class ProductOperateLogServiceImpl extends ServiceImpl<ProductOperateLogDao, ProductOperateLogEntity> implements ProductOperateLogService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ProductOperateLogEntity> page = this.page(
                new Query<ProductOperateLogEntity>().getPage(params),
                new QueryWrapper<ProductOperateLogEntity>()
        );

        return new PageUtils(page);
    }

}