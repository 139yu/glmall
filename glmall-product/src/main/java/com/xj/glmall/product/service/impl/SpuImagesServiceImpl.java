package com.xj.glmall.product.service.impl;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xj.glmall.common.utils.PageUtils;
import com.xj.glmall.common.utils.Query;

import com.xj.glmall.product.dao.SpuImagesDao;
import com.xj.glmall.product.entity.SpuImagesEntity;
import com.xj.glmall.product.service.SpuImagesService;


@Service("spuImagesService")
public class SpuImagesServiceImpl extends ServiceImpl<SpuImagesDao, SpuImagesEntity> implements SpuImagesService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SpuImagesEntity> page = this.page(
                new Query<SpuImagesEntity>().getPage(params),
                new QueryWrapper<SpuImagesEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveImages(Long spuId, List<String> images) {
        if (images != null && images.size() > 0) {
            List<SpuImagesEntity> collect = images.stream().map(item -> {
                SpuImagesEntity spuImagesEntity = new SpuImagesEntity();
                spuImagesEntity.setSpuId(spuId);
                spuImagesEntity.setImgUrl(item);
                return spuImagesEntity;
            }).collect(Collectors.toList());
            this.saveBatch(collect);
        }
    }

}
