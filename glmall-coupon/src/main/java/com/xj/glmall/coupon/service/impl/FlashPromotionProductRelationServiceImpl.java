package com.xj.glmall.coupon.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.common.utils.Query;

import com.xj.glmall.coupon.dao.FlashPromotionProductRelationDao;
import com.xj.glmall.coupon.entity.FlashPromotionProductRelationEntity;
import com.xj.glmall.coupon.service.FlashPromotionProductRelationService;


@Service("flashPromotionProductRelationService")
public class FlashPromotionProductRelationServiceImpl extends ServiceImpl<FlashPromotionProductRelationDao, FlashPromotionProductRelationEntity> implements FlashPromotionProductRelationService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<FlashPromotionProductRelationEntity> page = this.page(
                new Query<FlashPromotionProductRelationEntity>().getPage(params),
                new QueryWrapper<FlashPromotionProductRelationEntity>()
        );

        return new PageUtils(page);
    }

}