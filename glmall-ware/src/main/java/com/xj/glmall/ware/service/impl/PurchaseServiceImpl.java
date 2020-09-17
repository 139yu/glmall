package com.xj.glmall.ware.service.impl;

import com.xj.glmall.common.constant.WareConstant;
import com.xj.glmall.ware.entity.PurchaseDetailEntity;
import com.xj.glmall.ware.service.PurchaseDetailService;
import com.xj.glmall.ware.service.WareSkuService;
import com.xj.glmall.ware.vo.MergeVo;
import com.xj.glmall.ware.vo.PurchaseDoneItemVo;
import com.xj.glmall.ware.vo.PurchaseDoneVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.common.utils.Query;

import com.xj.glmall.ware.dao.PurchaseDao;
import com.xj.glmall.ware.entity.PurchaseEntity;
import com.xj.glmall.ware.service.PurchaseService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;


@Service("purchaseService")
public class PurchaseServiceImpl extends ServiceImpl<PurchaseDao, PurchaseEntity> implements PurchaseService {
    @Autowired
    private PurchaseDetailService purchaseDetailService;
    @Autowired
    private WareSkuService wareSkuService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<PurchaseEntity> page = this.page(
                new Query<PurchaseEntity>().getPage(params),
                new QueryWrapper<PurchaseEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryUnReceiveListPage(Map<String, Object> params) {
        QueryWrapper<PurchaseEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("status",0).or().eq("status",1);
        IPage<PurchaseEntity> page = this.page(
                new Query<PurchaseEntity>().getPage(params),
                wrapper
        );

        return new PageUtils(page);
    }

    @Transactional
    @Override
    public void merge(MergeVo mergeVo) {
        Long purchaseId = mergeVo.getPurchaseId();
        if (purchaseId == null) {
            PurchaseEntity purchaseEntity = new PurchaseEntity();
            purchaseEntity.setCreateTime(new Date());
            purchaseEntity.setUpdateTime(new Date());
            purchaseEntity.setStatus(WareConstant.PurchaseStatusEnum.CREATED.getCode());
            this.save(purchaseEntity);
            purchaseId = purchaseEntity.getId();
        }
        Long finalPurchaseId = purchaseId;
        List<PurchaseDetailEntity> collect = mergeVo.getItems().stream().map(id -> {
            PurchaseDetailEntity purchaseDetailEntity = new PurchaseDetailEntity();
            purchaseDetailEntity.setId(id);
            purchaseDetailEntity.setPurchaseId(finalPurchaseId);
            purchaseDetailEntity.setStatus(WareConstant.PurchaseDetailsStatusEnum.ASSIGNED.getCode());
            return purchaseDetailEntity;
        }).collect(Collectors.toList());
        purchaseDetailService.updateBatchById(collect);
        PurchaseEntity purchaseEntity = new PurchaseEntity();
        purchaseEntity.setId(purchaseId);
        purchaseEntity.setUpdateTime(new Date());
        this.updateById(purchaseEntity);
    }
    @Transactional
    @Override
    public void receivedPurchase(List<Long> ids) {
        //修改采购单状态
        List<PurchaseEntity> purchaseEntityList = ids.stream().map(id -> {
            return this.getById(id);
        }).filter(item -> {
            if (item.getStatus() == WareConstant.PurchaseStatusEnum.CREATED.getCode() ||
                    item.getStatus() == WareConstant.PurchaseStatusEnum.ASSIGNED.getCode()) {
                System.out.println("item:");
                System.out.println(item);
                return true;
            }else return false;
        }).map(item -> {
            System.out.println("item:" + item);
            item.setStatus(WareConstant.PurchaseStatusEnum.BUYING.getCode());
            item.setUpdateTime(new Date());
            return item;
        }).collect(Collectors.toList());
        System.out.println(purchaseEntityList);
        if (purchaseEntityList != null && purchaseEntityList.size() > 0) {
            this.updateBatchById(purchaseEntityList);
            purchaseEntityList.forEach(item -> {
                List<PurchaseDetailEntity> entityList = purchaseDetailService.listDetailsByPurchaseId(item.getId());
                if (entityList != null && entityList.size() > 0) {
                    List<Object> collect = entityList.stream().map(entity -> {
                        PurchaseDetailEntity purchaseDetailEntity = new PurchaseDetailEntity();
                        purchaseDetailEntity.setId(entity.getId());
                        purchaseDetailEntity.setStatus(WareConstant.PurchaseDetailsStatusEnum.BUYING.getCode());
                        return purchaseDetailEntity;
                    }).collect(Collectors.toList());
                }

                purchaseDetailService.updateBatchById(entityList);
            });
        }
    }
    @Transactional
    @Override
    public void finishPurchase(PurchaseDoneVo vo) {
        Long id = vo.getId();
        //改变采购单状态
        Boolean flag = true;
        //改变采购项状态
        List<PurchaseDetailEntity> updateList = new ArrayList<>();
        List<PurchaseDoneItemVo> items = vo.getItems();
        for (PurchaseDoneItemVo item: items) {
            PurchaseDetailEntity entity = new PurchaseDetailEntity();
            entity.setId(item.getItemId());
            entity.setStatus(item.getStatus());
            //采购失败
            if (item.getStatus() == WareConstant.PurchaseDetailsStatusEnum.HASERROR.getCode()) {
                flag = false;
            }else {
                //采购成功，将采购的商品入库
                PurchaseDetailEntity detailEntity = purchaseDetailService.getById(item.getItemId());
                wareSkuService.addStock(detailEntity.getSkuId(), Long.valueOf(detailEntity.getSkuNum()),detailEntity.getWareId());
            }
        }
        if(flag) {
            PurchaseEntity purchaseEntity = new PurchaseEntity();
            purchaseEntity.setId(id);
            purchaseEntity.setStatus(3);
            this.baseMapper.updateById(purchaseEntity);
        }
    }

}
