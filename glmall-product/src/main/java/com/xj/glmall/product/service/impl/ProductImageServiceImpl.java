package com.xj.glmall.product.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.common.utils.Query;

import com.xj.glmall.product.dao.ProductImageDao;
import com.xj.glmall.product.entity.ProductImageEntity;
import com.xj.glmall.product.service.ProductImageService;


@Service("productImageService")
public class ProductImageServiceImpl extends ServiceImpl<ProductImageDao, ProductImageEntity> implements ProductImageService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ProductImageEntity> page = this.page(
                new Query<ProductImageEntity>().getPage(params),
                new QueryWrapper<ProductImageEntity>()
        );

        return new PageUtils(page);
    }

}