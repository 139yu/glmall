package com.xj.glmall.product.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.common.utils.Query;

import com.xj.glmall.product.dao.ProductSaleAttrDao;
import com.xj.glmall.product.entity.ProductSaleAttrEntity;
import com.xj.glmall.product.service.ProductSaleAttrService;


@Service("productSaleAttrService")
public class ProductSaleAttrServiceImpl extends ServiceImpl<ProductSaleAttrDao, ProductSaleAttrEntity> implements ProductSaleAttrService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ProductSaleAttrEntity> page = this.page(
                new Query<ProductSaleAttrEntity>().getPage(params),
                new QueryWrapper<ProductSaleAttrEntity>()
        );

        return new PageUtils(page);
    }

}