package com.xj.glmall.product.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.common.utils.Query;

import com.xj.glmall.product.dao.BaseAttrValueDao;
import com.xj.glmall.product.entity.BaseAttrValueEntity;
import com.xj.glmall.product.service.BaseAttrValueService;


@Service("baseAttrValueService")
public class BaseAttrValueServiceImpl extends ServiceImpl<BaseAttrValueDao, BaseAttrValueEntity> implements BaseAttrValueService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<BaseAttrValueEntity> page = this.page(
                new Query<BaseAttrValueEntity>().getPage(params),
                new QueryWrapper<BaseAttrValueEntity>()
        );

        return new PageUtils(page);
    }

}