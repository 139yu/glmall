package com.xj.glmall.product.service.impl;

import com.xj.glmall.product.vo.SkuItemSaleAttrVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.common.utils.Query;

import com.xj.glmall.product.dao.SkuSaleAttrValueDao;
import com.xj.glmall.product.entity.SkuSaleAttrValueEntity;
import com.xj.glmall.product.service.SkuSaleAttrValueService;


@Service("skuSaleAttrValueService")
public class SkuSaleAttrValueServiceImpl extends ServiceImpl<SkuSaleAttrValueDao, SkuSaleAttrValueEntity> implements SkuSaleAttrValueService {

    @Autowired
    private SkuSaleAttrValueDao skuSaleAttrValueDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SkuSaleAttrValueEntity> page = this.page(
                new Query<SkuSaleAttrValueEntity>().getPage(params),
                new QueryWrapper<SkuSaleAttrValueEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<SkuItemSaleAttrVo> listSkuSaleAttrValueBySpuId(Long spuId) {
        List<SkuItemSaleAttrVo> saleAttrValueEntities = skuSaleAttrValueDao.listSkuSaleAttrValueBySpuId(spuId);
        return saleAttrValueEntities;
    }

    @Override
    public List<String> listStringSaleAttr(Long skuId) {
        SkuSaleAttrValueDao baseMapper = this.baseMapper;
        return baseMapper.listStringSaleAttr(skuId);
    }

}