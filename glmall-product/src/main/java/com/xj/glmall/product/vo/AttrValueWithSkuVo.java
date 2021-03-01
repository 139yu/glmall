package com.xj.glmall.product.vo;

import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
public class AttrValueWithSkuVo {
    private String attrValue;
    private String skuIds;
}
