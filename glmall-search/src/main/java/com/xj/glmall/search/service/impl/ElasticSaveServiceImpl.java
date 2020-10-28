package com.xj.glmall.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.xj.glmall.common.to.es.SkuEsModel;
import com.xj.glmall.search.config.GlmallElasticsearchConfig;
import com.xj.glmall.search.constant.EsConstant;
import com.xj.glmall.search.service.ElasticSaveService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ElasticSaveServiceImpl implements ElasticSaveService {
    @Autowired
    private RestHighLevelClient restHighLevelClient;
    @Override
    public Boolean productStatusUp(List<SkuEsModel> skuEsModelList) throws IOException {
        BulkRequest bulkRequest = new BulkRequest();
        for (SkuEsModel skuEsModel : skuEsModelList) {
            IndexRequest indexRequest = new IndexRequest(EsConstant.PRODUCT_INDEX);
            indexRequest.id(skuEsModel.getSkuId().toString());
            String s = JSON.toJSONString(skuEsModel);
            System.out.println(s);
            indexRequest.source(s,XContentType.JSON);
            bulkRequest.add(indexRequest);
        }
        BulkResponse bulkResponse = restHighLevelClient.bulk(bulkRequest, GlmallElasticsearchConfig.COMMON_OPTIONS);
        boolean b = bulkResponse.hasFailures();
        if (b) {
            List<String> collect = Arrays.stream(bulkResponse.getItems()).map(item -> {
                return item.getId();
            }).collect(Collectors.toList());
            log.error("商品上架错误：{}",collect);
            return false;
        }else {
            return true;
        }

    }
}
