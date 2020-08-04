package com.xj.glmall.product.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.common.utils.Query;

import com.xj.glmall.product.dao.ProductLadderDao;
import com.xj.glmall.product.entity.ProductLadderEntity;
import com.xj.glmall.product.service.ProductLadderService;


@Service("productLadderService")
public class ProductLadderServiceImpl extends ServiceImpl<ProductLadderDao, ProductLadderEntity> implements ProductLadderService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ProductLadderEntity> page = this.page(
                new Query<ProductLadderEntity>().getPage(params),
                new QueryWrapper<ProductLadderEntity>()
        );

        return new PageUtils(page);
    }

}