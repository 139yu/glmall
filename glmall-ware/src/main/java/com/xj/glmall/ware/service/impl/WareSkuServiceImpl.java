package com.xj.glmall.ware.service.impl;

import com.xj.glmall.common.to.SkuInfoTo;
import com.xj.glmall.common.utils.R;
import com.xj.glmall.ware.feign.ProductServiceFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.common.utils.Query;

import com.xj.glmall.ware.dao.WareSkuDao;
import com.xj.glmall.ware.entity.WareSkuEntity;
import com.xj.glmall.ware.service.WareSkuService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


@Service("wareSkuService")
public class WareSkuServiceImpl extends ServiceImpl<WareSkuDao, WareSkuEntity> implements WareSkuService {
    @Autowired
    private WareSkuDao wareSkuDao;
    @Autowired
    private ProductServiceFeign productServiceFeign;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<WareSkuEntity> wrapper = new QueryWrapper<>();
        String wareId = (String) params.get("wareId");
        String skuId = (String) params.get("skuId");
        if (!StringUtils.isEmpty(skuId)) {
            wrapper.eq("sku_id",skuId);
        }
        if (!StringUtils.isEmpty("wareId")) {
            wrapper.eq("ware_id",wareId);
        }
        IPage<WareSkuEntity> page = this.page(
                new Query<WareSkuEntity>().getPage(params),
                wrapper
        );

        return new PageUtils(page);
    }
    @Transactional
    @Override
    public void addStock(Long skuId, Long skuNum, Long wareId) {
        QueryWrapper<WareSkuEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sku_id",skuId).eq("ware_id",wareId);
        Integer count = this.baseMapper.selectCount(queryWrapper);
        if (count <= 0) {
            WareSkuEntity wareSkuEntity = new WareSkuEntity();
            try{
                R r = productServiceFeign.getSkuInfo(skuId);
                System.out.println();
                Map<String,Object> data = (Map<String, Object>) r.get("skuInfo");
                if (r.getCode() == 0) {
                    wareSkuEntity.setSkuName((String) data.get("skuName"));
                }

            }catch (Exception e) {

            }
            wareSkuEntity.setWareId(wareId);
            wareSkuEntity.setSkuId(skuId);
            wareSkuEntity.setStock(Math.toIntExact(skuNum));
            this.baseMapper.insert(wareSkuEntity);
        }else {
            wareSkuDao.addStock(skuId, Math.toIntExact(skuNum),wareId);
        }
    }

}
