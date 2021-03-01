package com.xj.glmall.search.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
public class SearchParam {
    private String keyword;//关键字
    private String catalog3Id;//三级分类Id
    /**
     * 排序条件，只能有一个排序条件
     * sort=saleCount_asc/saleCount_desc
     */
    private String sort;

    /**
     * 过滤条件：
     * hasStock（是否有货），skuPrice价格区间，brandId，catalog3Id，attrs
     * hasStock=0/1
     * skuPrice=1_500（1到500）
     * brand=1&brand=2
     * attrs=2_5.56寸（2号属性，5.56寸）
     */
    private Integer hasStock;//0：无库存，1：有库存
    private String skuPrice;
    private List<Long> brandId;
    private List<String> attrs;
    private Integer pageNum = 1;//当前页码
    private String _queryString;//原生查询参数

}
