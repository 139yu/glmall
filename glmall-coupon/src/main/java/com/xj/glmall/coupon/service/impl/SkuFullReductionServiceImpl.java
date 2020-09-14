package com.xj.glmall.coupon.service.impl;

import com.xj.glmall.common.to.MemberPrice;
import com.xj.glmall.common.to.SkuReductionTo;
import com.xj.glmall.coupon.dao.SkuLadderDao;
import com.xj.glmall.coupon.entity.MemberPriceEntity;
import com.xj.glmall.coupon.entity.SkuLadderEntity;
import com.xj.glmall.coupon.service.MemberPriceService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.common.utils.Query;

import com.xj.glmall.coupon.dao.SkuFullReductionDao;
import com.xj.glmall.coupon.entity.SkuFullReductionEntity;
import com.xj.glmall.coupon.service.SkuFullReductionService;
import org.springframework.transaction.annotation.Transactional;


@Service("skuFullReductionService")
public class SkuFullReductionServiceImpl extends ServiceImpl<SkuFullReductionDao, SkuFullReductionEntity> implements SkuFullReductionService {
    @Autowired
    private SkuLadderDao skuLadderDao;
    @Autowired
    private SkuFullReductionDao skuFullReductionDao;
    @Autowired
    private MemberPriceService memberPriceService;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SkuFullReductionEntity> page = this.page(
                new Query<SkuFullReductionEntity>().getPage(params),
                new QueryWrapper<SkuFullReductionEntity>()
        );

        return new PageUtils(page);
    }

    @Transactional
    @Override
    public void saveReductionInfo(SkuReductionTo skuReductionTo) {
        if (skuReductionTo.getFullPrice().compareTo(new BigDecimal("0")) ==1 ){
            SkuLadderEntity skuLadderEntity = new SkuLadderEntity();
            BeanUtils.copyProperties(skuReductionTo,skuLadderEntity);
            skuLadderEntity.setAddOther(skuReductionTo.getCountStatus());
            skuLadderDao.insert(skuLadderEntity);
        }
        if (skuReductionTo.getFullCount() > 0) {
            SkuFullReductionEntity skuFullReductionEntity = new SkuFullReductionEntity();
            BeanUtils.copyProperties(skuReductionTo,skuFullReductionEntity);
            skuFullReductionEntity.setAddOther(skuReductionTo.getCountStatus());
            skuFullReductionDao.insert(skuFullReductionEntity);
        }

        List<MemberPrice> memberPrice = skuReductionTo.getMemberPrice();
        List<MemberPriceEntity> collect = memberPrice.stream().map(item -> {
            MemberPriceEntity memberPriceEntity = new MemberPriceEntity();
            memberPriceEntity.setMemberLevelName(item.getName());
            memberPriceEntity.setSkuId(skuReductionTo.getSkuId());
            memberPriceEntity.setMemberPrice(item.getPrice());
            memberPriceEntity.setId(item.getId());
            memberPriceEntity.setAddOther(1);
            return memberPriceEntity;
        }).filter(entity -> {
            return entity.getMemberPrice().compareTo(new BigDecimal(0)) == 1;
        }).collect(Collectors.toList());
        memberPriceService.saveBatch(collect);
    }

}
