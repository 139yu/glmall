package com.xj.glmall.search;

import com.alibaba.fastjson.JSON;
import com.xj.glmall.search.config.GlmallElasticsearchConfig;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.Avg;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GlmallSearchTest {

    @Autowired
    private RestHighLevelClient client;

    @Test
    public void testSearchApi() throws IOException {
        SearchRequest request = new SearchRequest("bank");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        //sourceBuilder.query(QueryBuilders.matchQuery("address","Humboldt Street"));
        sourceBuilder.query(QueryBuilders.matchAllQuery());
        TermsAggregationBuilder ageAgg = AggregationBuilders.terms("age_agg").field("age").size(10);
        ageAgg.subAggregation(AggregationBuilders.avg("balance_avg").field("balance"));
        sourceBuilder.aggregation(ageAgg);
        sourceBuilder.from(0);
        sourceBuilder.size(10);
        request.source(sourceBuilder);
        SearchResponse response = client.search(request, GlmallElasticsearchConfig.COMMON_OPTIONS);
        Aggregations aggregations = response.getAggregations();
        Terms age_agg = aggregations.get("age_agg");
        for (Terms.Bucket bucket : age_agg.getBuckets()) {
            Avg balanceAvg = bucket.getAggregations().get("balance_avg");
            String name = balanceAvg.getName();
            System.out.println(balanceAvg.getValueAsString());
        }
        System.out.println(age_agg);
        System.out.println(response);
    }

    @Test
    public void testGet() throws IOException {
        GetRequest request = new GetRequest("user", "1");
        GetResponse response = client.get(request, GlmallElasticsearchConfig.COMMON_OPTIONS);
        System.out.println(response);
    }
    @Test
    public void testIndex() throws IOException {
        User user = new User();
        user.setName("jone");
        user.setAge(18);
        user.setAddress("南昌");
        //将对象转为json格式字符串
        String jsonString = JSON.toJSONString(user);
        IndexRequest request = new IndexRequest("user");//索引
        request.id("2");//id
        //传递json格式字符串时要指定content type为JSON
        request.source(jsonString,XContentType.JSON);
        IndexResponse index = client.index(request, GlmallElasticsearchConfig.COMMON_OPTIONS);
        System.out.println(index);
    }

    @Test
    public void testEsClient(){
        System.out.println(client);
    }
}
