package com.xj.glmall.coupon.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.common.utils.Query;

import com.xj.glmall.coupon.dao.FlashPromotionSessionDao;
import com.xj.glmall.coupon.entity.FlashPromotionSessionEntity;
import com.xj.glmall.coupon.service.FlashPromotionSessionService;


@Service("flashPromotionSessionService")
public class FlashPromotionSessionServiceImpl extends ServiceImpl<FlashPromotionSessionDao, FlashPromotionSessionEntity> implements FlashPromotionSessionService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<FlashPromotionSessionEntity> page = this.page(
                new Query<FlashPromotionSessionEntity>().getPage(params),
                new QueryWrapper<FlashPromotionSessionEntity>()
        );

        return new PageUtils(page);
    }

}