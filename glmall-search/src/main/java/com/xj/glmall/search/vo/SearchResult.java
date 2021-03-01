package com.xj.glmall.search.vo;

import com.xj.glmall.common.to.es.SkuEsModel;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Data
public class SearchResult {

    private List<SkuEsModel> products;
    /**
     * 分页信息
     */
    private Long total;//总记录数
    private Integer pageNum;//页码
    private Integer totalPages;//总页码
    private List<Integer> pageNavs;

    //当前查询结果涉及品牌
    private List<BrandVo> brands;
    //当前结果涉及属性
    private List<AttrVo> attrs;
    //当前结果涉及的分类
    private List<CatalogVo> catalogs;
    //面包屑导航
    private List<NavVo> navVoList = new ArrayList<>();
    private List<String> attrIds = new ArrayList<>();

    @Data
    @Accessors(chain = true)
    public static class NavVo{
        private String navId;
        private String navName;
        private String navValue;
        private String navUrl;
    }

    @Data
    public static class BrandVo{
        private String brandId;
        private String brandName;
        private String brandImg;
    }

    @Data
    public static class AttrVo{
        private String attrId;
        private String attrName;
        private List<String> attrValue;
    }

    @Data
    public static class CatalogVo{
        private String catalogId;
        private String catalogName;
    }
}
