package com.xj.glmall.product.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.common.utils.Query;

import com.xj.glmall.product.dao.SkuAttrValueDao;
import com.xj.glmall.product.entity.SkuAttrValueEntity;
import com.xj.glmall.product.service.SkuAttrValueService;


@Service("skuAttrValueService")
public class SkuAttrValueServiceImpl extends ServiceImpl<SkuAttrValueDao, SkuAttrValueEntity> implements SkuAttrValueService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SkuAttrValueEntity> page = this.page(
                new Query<SkuAttrValueEntity>().getPage(params),
                new QueryWrapper<SkuAttrValueEntity>()
        );

        return new PageUtils(page);
    }

}