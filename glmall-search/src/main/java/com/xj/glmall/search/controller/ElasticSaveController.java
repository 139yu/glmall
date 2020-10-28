package com.xj.glmall.search.controller;

import com.xj.glmall.common.exception.BizCodeEnum;
import com.xj.glmall.common.to.es.SkuEsModel;
import com.xj.glmall.common.utils.R;
import com.xj.glmall.search.service.ElasticSaveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/search/save")
@Slf4j
public class ElasticSaveController {
    @Autowired
    private ElasticSaveService elasticSaveService;
    @PostMapping("/product")
    public R productSave(@RequestBody List<SkuEsModel> skuEsModelList){
        boolean b = false;
        try {
            b = elasticSaveService.productStatusUp(skuEsModelList);
        } catch (IOException e) {
            log.error("ElasticSaveController商品上架失败：{}",e);
            return R.error(BizCodeEnum.PRODUCT_UP_ERRPR.getCode(),BizCodeEnum.PRODUCT_UP_ERRPR.getMessage());
        }
        if (b) return R.ok();
        else return R.error(BizCodeEnum.PRODUCT_UP_ERRPR.getCode(),BizCodeEnum.PRODUCT_UP_ERRPR.getMessage());
    }
}
