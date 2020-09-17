package com.xj.glmall.ware.service.impl;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.common.utils.Query;

import com.xj.glmall.ware.dao.PurchaseDetailDao;
import com.xj.glmall.ware.entity.PurchaseDetailEntity;
import com.xj.glmall.ware.service.PurchaseDetailService;
import org.springframework.util.StringUtils;


@Service("purchaseDetailService")
public class PurchaseDetailServiceImpl extends ServiceImpl<PurchaseDetailDao, PurchaseDetailEntity> implements PurchaseDetailService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<PurchaseDetailEntity> wrapper = new QueryWrapper<>();
        String key = (String) params.get("key");
        String status = (String) params.get("status");
        String wareId = (String) params.get("wareId");
        if (!StringUtils.isEmpty(status)) {
            wrapper.eq("status",status);
        }
        if (!StringUtils.isEmpty(wareId)) {
            wrapper.eq("ware_id",wareId);
        }
        if (!StringUtils.isEmpty(key)) {
            wrapper.and(obj -> {
                obj.eq("id",key).or().eq("purchase_id",key).or().eq("sku_id",key).or().eq("sku_nun",key).or().eq("sku_price",key);
            });
        }
        IPage<PurchaseDetailEntity> page = this.page(
                new Query<PurchaseDetailEntity>().getPage(params),
                wrapper
        );

        return new PageUtils(page);
    }

    @Override
    public List<PurchaseDetailEntity> listDetailsByPurchaseId(Long id) {
        QueryWrapper<PurchaseDetailEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("purchase_id",id);
        return this.baseMapper.selectList(queryWrapper);
    }

}
