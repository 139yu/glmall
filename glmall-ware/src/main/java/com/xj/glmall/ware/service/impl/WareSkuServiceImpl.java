package com.xj.glmall.ware.service.impl;

import com.xj.glmall.common.to.SkuInfoTo;
import com.xj.glmall.common.utils.R;
import com.xj.glmall.ware.feign.ProductServiceFeign;
import com.xj.glmall.ware.vo.SkuHasStockVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        if (!StringUtils.isEmpty(wareId)) {
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

    @Override
    public void saveWareSku(WareSkuEntity wareSku) {
        QueryWrapper<WareSkuEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("sku_id",wareSku.getSkuId());
        wrapper.eq("ware_id",wareSku.getWareId());
        WareSkuEntity entity = this.baseMapper.selectOne(wrapper);
        if (entity == null) {
            this.baseMapper.insert(wareSku);
        }else {
            wareSku.setStock(wareSku.getStock() + entity.getStock());
            wareSku.setStockLocked(wareSku.getStockLocked() + entity.getStockLocked());
            wareSku.setId(entity.getId());
            this.baseMapper.updateById(wareSku);
        }
     }

    @Override
    public Integer getSkuStock(Long skuId) {
        QueryWrapper<WareSkuEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("sku_id",skuId);
        List<WareSkuEntity> skuEntities = this.baseMapper.selectList(wrapper);
        int stock = 0;
        if (skuEntities == null || skuEntities.size() <= 0) {
            return 0;
        }else {
            for (WareSkuEntity skuEntity : skuEntities) {
                stock += skuEntity.getStock();
            }
            return stock;
        }

    }

    @Override
    public List<SkuHasStockVo> getSkuHasStock(List<Long> skuIds) {
        List<SkuHasStockVo> collect = skuIds.stream().map(item -> {
            SkuHasStockVo hasStockVo = new SkuHasStockVo();
            hasStockVo.setSkuId(item);
            Long stock = baseMapper.getStockBySkuId(item);
            if (null == stock) {
                hasStockVo.setHasStack(false);
            }else {
                hasStockVo.setHasStack(stock > 0);
            }
            return hasStockVo;
        }).collect(Collectors.toList());
        return collect;
    }

}
