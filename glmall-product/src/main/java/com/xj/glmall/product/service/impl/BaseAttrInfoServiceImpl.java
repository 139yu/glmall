package com.xj.glmall.product.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.common.utils.Query;

import com.xj.glmall.product.dao.BaseAttrInfoDao;
import com.xj.glmall.product.entity.BaseAttrInfoEntity;
import com.xj.glmall.product.service.BaseAttrInfoService;


@Service("baseAttrInfoService")
public class BaseAttrInfoServiceImpl extends ServiceImpl<BaseAttrInfoDao, BaseAttrInfoEntity> implements BaseAttrInfoService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<BaseAttrInfoEntity> page = this.page(
                new Query<BaseAttrInfoEntity>().getPage(params),
                new QueryWrapper<BaseAttrInfoEntity>()
        );

        return new PageUtils(page);
    }

}