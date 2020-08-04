package com.xj.glmall.coupon.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.common.utils.Query;

import com.xj.glmall.coupon.dao.FlashPromotionLogDao;
import com.xj.glmall.coupon.entity.FlashPromotionLogEntity;
import com.xj.glmall.coupon.service.FlashPromotionLogService;


@Service("flashPromotionLogService")
public class FlashPromotionLogServiceImpl extends ServiceImpl<FlashPromotionLogDao, FlashPromotionLogEntity> implements FlashPromotionLogService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<FlashPromotionLogEntity> page = this.page(
                new Query<FlashPromotionLogEntity>().getPage(params),
                new QueryWrapper<FlashPromotionLogEntity>()
        );

        return new PageUtils(page);
    }

}