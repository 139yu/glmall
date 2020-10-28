package com.xj.glmall.search.service;

import com.xj.glmall.common.to.es.SkuEsModel;

import java.io.IOException;
import java.util.List;

public interface ElasticSaveService {
    public Boolean productStatusUp(List<SkuEsModel> skuEsModelList) throws IOException;
}
