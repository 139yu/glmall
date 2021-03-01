package com.xj.glmall.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.xj.glmall.common.to.es.SkuEsModel;
import com.xj.glmall.common.utils.R;
import com.xj.glmall.search.config.GlmallElasticsearchConfig;
import com.xj.glmall.search.constant.EsConstant;
import com.xj.glmall.search.feign.ProductFeignClient;
import com.xj.glmall.search.service.MallSearchService;
import com.xj.glmall.search.vo.AttrRespVo;
import com.xj.glmall.search.vo.BrandVo;
import com.xj.glmall.search.vo.SearchParam;
import com.xj.glmall.search.vo.SearchResult;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.NestedQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.nested.NestedAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.nested.ParsedNested;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedLongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MallSearchServiceImpl implements MallSearchService {

    @Autowired
    private RestHighLevelClient esRestClient;
    @Autowired
    private ProductFeignClient productFeignClient;

    @Override
    public SearchResult listPage(SearchParam searchParam) throws IOException {
        SearchRequest searchRequest = buildSearchRequest(searchParam);
        SearchResponse search = esRestClient.search(searchRequest,GlmallElasticsearchConfig.COMMON_OPTIONS);
        SearchResult searchResult = buildSearchResult(search,searchParam);
        return searchResult;
    }

    /**
     * 构建查询条件
     * 1.模糊匹配
     * 2.过滤
     *  1）分类过滤
     *  2）品牌过滤
     *  3）价格区间过滤
     *  4）商品属性过滤
     *  5）库存
     * 3.排序
     * 4.分页
     * 5.高亮显示
     * 6.聚合分析
     * @param param
     * @return
     */
    private SearchRequest buildSearchRequest(SearchParam param){
        SearchRequest searchRequest = new SearchRequest(EsConstant.PRODUCT_INDEX);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        //1.模糊匹配
        if(!StringUtils.isEmpty(param.getKeyword())) {

            boolQuery.must(QueryBuilders.matchQuery("skuTitle",param.getKeyword()));
            //高亮显示
            HighlightBuilder highlightBuilder = new HighlightBuilder();
            highlightBuilder.field("skuTitle").preTags("<b style='color:red'>").postTags("</b>");
            sourceBuilder.highlighter(highlightBuilder);
        }
        //2.过滤
        if (!StringUtils.isEmpty(param.getCatalog3Id())) {
            //分类过滤
            boolQuery.filter(QueryBuilders.termQuery("catalogId",param.getCatalog3Id()));
        }
        if (!CollectionUtils.isEmpty(param.getBrandId())) {
            //品牌过滤
            boolQuery.filter(QueryBuilders.termsQuery("brandId",param.getBrandId()));
        }
        if (!StringUtils.isEmpty(param.getSkuPrice())){
            //价格过滤
            RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery("skuPrice");
            String[] s = param.getSkuPrice().split("_");
            if (s.length == 2) {
                rangeQuery.gte(s[0]).lte(s[1]);
            }else if (s.length ==1){
                if (param.getSkuPrice().startsWith("_")){
                    rangeQuery.lte(s[0]);
                }else {
                    rangeQuery.gte(s[1]);
                }
            }else {
                throw new IllegalArgumentException("参数非法");
            }
            boolQuery.filter(rangeQuery);
        }
        //是否有库存
        if (param.getHasStock() != null ) {
            boolQuery.filter(QueryBuilders.termsQuery("hasStock",param.getHasStock() == 1));
        }
        //属性过滤
        if (!CollectionUtils.isEmpty(param.getAttrs())){
            for (String attr : param.getAttrs()) {
                BoolQueryBuilder nestedBoolQuery = QueryBuilders.boolQuery();
                String[] s = attr.split("_");
                String attrId = s[0];
                final String[] attrValues = s[1].split(":");
                nestedBoolQuery.must(QueryBuilders.termQuery("attrs.attrId",attrId))
                        .must(QueryBuilders.termsQuery("attrs.attrValue",attrValues));
                boolQuery.filter(QueryBuilders.nestedQuery("attrs",nestedBoolQuery,ScoreMode.None));
            }
        }
        sourceBuilder.query(boolQuery);
        //排序
        if (!StringUtils.isEmpty(param.getSort())) {
            String[] s = param.getSort().split("_");
            FieldSortBuilder fieldSort = SortBuilders.fieldSort(s[0]);
            if (SortOrder.ASC.toString().equalsIgnoreCase(s[1])) {
                fieldSort.order(SortOrder.ASC);
            }else {
                fieldSort.order(SortOrder.DESC);
            }
            sourceBuilder.sort(fieldSort);
        }
        //分页
        sourceBuilder.from((param.getPageNum() - 1) * EsConstant.PRODUCT_PAGESIZE);
        sourceBuilder.size(EsConstant.PRODUCT_PAGESIZE);
         /**
         * 聚合
         */
        //1.品牌聚合
        TermsAggregationBuilder brandAgg = AggregationBuilders.terms("brand_agg")
                .size(50)
                .field("brandId");
        //品牌子聚合
        TermsAggregationBuilder brandNameAgg = AggregationBuilders.terms("brand_name_agg").size(50).field("brandName");
        TermsAggregationBuilder brandImgAgg = AggregationBuilders.terms("brand_img_agg").size(50).field("brandImg");
        brandAgg.subAggregation(brandNameAgg).subAggregation(brandImgAgg);
        //2.分类聚合
        TermsAggregationBuilder catalogAgg = AggregationBuilders.terms("catalog_agg").size(50).field("catalogId");
        //分类子聚合
        TermsAggregationBuilder catalogNameAgg = AggregationBuilders.terms("catalog_name_agg").size(50).field("catalogName");
        catalogAgg.subAggregation(catalogNameAgg);
        //3.属性聚合
        NestedAggregationBuilder attrAgg = AggregationBuilders.nested("attr_agg", "attrs");
        TermsAggregationBuilder attrIdAgg = AggregationBuilders.terms("attr_id_agg").size(50).field("attrs.attrId");
        TermsAggregationBuilder attrNameAgg = AggregationBuilders.terms("attr_name_agg").size(50).field("attrs.attrName");
        TermsAggregationBuilder attrValueAgg = AggregationBuilders.terms("attr_value_agg").size(50).field("attrs.attrValue");
        attrIdAgg.subAggregation(attrNameAgg).subAggregation(attrValueAgg);
        attrAgg.subAggregation(attrIdAgg);
        sourceBuilder.aggregation(attrAgg).aggregation(brandAgg).aggregation(catalogAgg);
        searchRequest.source(sourceBuilder);
        System.out.println(sourceBuilder.toString());
        return searchRequest;
    }


    /**
     * 构建返回数据
     * @param searchResponse
     * @return
     * @throws IOException
     */
    private SearchResult buildSearchResult(SearchResponse searchResponse,SearchParam searchParam)  {
        System.out.println(searchResponse);
        SearchResult result = new SearchResult();
        SearchHits searchHits = searchResponse.getHits();
        //分页数据
        result.setTotal(searchHits.getTotalHits().value);
        int totalPage = 0;
        if (result.getTotal() > 0) {
            if (result.getTotal() < EsConstant.PRODUCT_PAGESIZE) {
                totalPage = 1;
            }else {
                if (result.getTotal() % EsConstant.PRODUCT_PAGESIZE != 0) {
                    totalPage = (int) (result.getTotal() / EsConstant.PRODUCT_PAGESIZE + 1);
                }else {
                    totalPage = (int) (result.getTotal() / EsConstant.PRODUCT_PAGESIZE);
                }
            }
        }
        result.setTotalPages(totalPage);
        result.setPageNum(searchParam.getPageNum());
        List<Integer> navs = new ArrayList<>();
        for (int i = 1; i <= totalPage; i++) {
            navs.add(i);
        }
        result.setPageNavs(navs);


        //构建属性面包屑导航信息
        if (!CollectionUtils.isEmpty(searchParam.getAttrs())){
            List<SearchResult.NavVo> navVoList = searchParam.getAttrs().stream().map(item -> {
                SearchResult.NavVo navVo = new SearchResult.NavVo();
                String[] s = item.split("_");
                String attrId = s[0];
                String attrValue = s[1];
                result.getAttrIds().add(attrId);
                R info = productFeignClient.info(Long.valueOf(attrId));
                if (info.getCode() == 0) {
                    AttrRespVo attr = (AttrRespVo) info.getData("attr", new TypeReference<AttrRespVo>() {
                    });
                    navVo.setNavName(attr.getAttrName());
                }else {
                    navVo.setNavName(attrId);
                }

                navVo.setNavId(attrId).setNavValue(attrValue);
                StringBuilder sb = new StringBuilder();
                sb.append("http://search.glmall.com/list.html?");
                String replace = encodeAndReplace(searchParam.get_queryString(), item,"attrs");
                System.out.println("query string:" + replace);
                sb.append(replace);
                navVo.setNavUrl(sb.toString());
                return navVo;
            }).collect(Collectors.toList());
            result.setNavVoList(navVoList);
        }
        //构建品牌面包屑导航
        List<Long> brandIds = searchParam.getBrandId();
        System.out.println(brandIds);
        SearchResult.NavVo navVo = new SearchResult.NavVo();
        if (!CollectionUtils.isEmpty(brandIds)) {
            R r = productFeignClient.listBrands(brandIds);
            List<SearchResult.NavVo> navVoList = result.getNavVoList();
            String replace = null;
            if (r.getCode() == 0) {
                List<BrandVo> brandList = (List<BrandVo>) r.getData("brandList", new TypeReference<List<BrandVo>>() {
                });
                System.out.println(brandList);
                StringBuilder sb = new StringBuilder();
                for (BrandVo brandVo : brandList) {
                    sb.append(brandVo.getName()).append(";");
                    replace = encodeAndReplace(searchParam.get_queryString(),brandVo.getBrandId()+"","brandId");
                }
                navVo.setNavValue(sb.toString());
            }
            navVo.setNavName("品牌");
            navVo.setNavUrl("http://search.glmall.com/list.html?" + replace);
            System.out.println("query string:" + replace);
            navVoList.add(navVo);
            System.out.println(navVoList);
            result.setNavVoList(navVoList);
        }
        //设置商品信息
        List<SkuEsModel> productList = new ArrayList<>();
        for (SearchHit hit : searchHits.getHits()) {
            String sourceAsString = hit.getSourceAsString();
            SkuEsModel skuEsModel = JSON.parseObject(sourceAsString, SkuEsModel.class);
            //设置高亮
            HighlightField skuTitle = hit.getHighlightFields().get("skuTitle");
            if (skuTitle != null && skuTitle.getFragments() != null && skuTitle.getFragments().length > 0 ) {
                String string = skuTitle.getFragments()[0].string();
                skuEsModel.setSkuTitle(string);
                }
            productList.add(skuEsModel);
        }
        result.setProducts(productList);
        //设置分类信息
        Aggregations aggregations = searchResponse.getAggregations();
        ParsedLongTerms catalogAgg = aggregations.get("catalog_agg");
        List<? extends Terms.Bucket> buckets = catalogAgg.getBuckets();
        List<SearchResult.CatalogVo> catalogVoList = new ArrayList<>();
        for (Terms.Bucket bucket : buckets) {
            SearchResult.CatalogVo catalogVo = new SearchResult.CatalogVo();
            catalogVo.setCatalogId(bucket.getKeyAsString());
            ParsedStringTerms catalogNameAgg = bucket.getAggregations().get("catalog_name_agg");
            catalogVo.setCatalogName(catalogNameAgg.getBuckets().get(0).getKeyAsString());
            catalogVoList.add(catalogVo);
        }
        result.setCatalogs(catalogVoList);
        //设置属性
        ParsedNested attrAgg = aggregations.get("attr_agg");
        ParsedLongTerms attrIdAgg = attrAgg.getAggregations().get("attr_id_agg");
        List<SearchResult.AttrVo> attrVoList = new ArrayList<>();
        List<? extends Terms.Bucket> attrIdAggBuckets = attrIdAgg.getBuckets();
        for (Terms.Bucket attrIdAggBucket : attrIdAggBuckets) {
            SearchResult.AttrVo attrVo = new SearchResult.AttrVo();
            attrVo.setAttrId(attrIdAggBucket.getKeyAsString());
            result.getAttrIds().add(attrVo.getAttrId());
            ParsedStringTerms attrNameAgg = attrIdAggBucket.getAggregations().get("attr_name_agg");
            attrVo.setAttrName(attrNameAgg.getBuckets().get(0).getKeyAsString());
            ParsedStringTerms attrValueAgg = attrIdAggBucket.getAggregations().get("attr_value_agg");
            List<String> attrValues = new ArrayList<>();
            for (Terms.Bucket valueAggBucket : attrValueAgg.getBuckets()) {
                attrValues.add(valueAggBucket.getKeyAsString());
            }
            attrVo.setAttrValue(attrValues);
            attrVoList.add(attrVo);
        }
        result.setAttrs(attrVoList);
        //设置品牌信息
        List<SearchResult.BrandVo>  brandVoList = new ArrayList<>();
        ParsedLongTerms brandAgg = aggregations.get("brand_agg");
        for (Terms.Bucket bucket : brandAgg.getBuckets()) {
            SearchResult.BrandVo brandVo = new SearchResult.BrandVo();
            brandVo.setBrandId(bucket.getKeyAsString());
            ParsedStringTerms brandImgAgg = bucket.getAggregations().get("brand_img_agg");
            brandVo.setBrandImg(brandImgAgg.getBuckets().get(0).getKeyAsString());
            ParsedStringTerms brandNameAgg = bucket.getAggregations().get("brand_name_agg");
            brandVo.setBrandName(brandNameAgg.getBuckets().get(0).getKeyAsString());
            brandVoList.add(brandVo);
        }
        result.setBrands(brandVoList);

        return result;
    }

    private String encodeAndReplace(String queryString, String value,String name) {
        String encode = null;
        try {
            encode = URLEncoder.encode(value,"UTF-8").replace("+", "%20");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (queryString.indexOf("?" + name + "=" + encode) != -1) {
            return queryString.replace("?" + name + "=" + encode, "");
        }
        return queryString.replace("&" + name + "=" + encode, "");
    }
}
