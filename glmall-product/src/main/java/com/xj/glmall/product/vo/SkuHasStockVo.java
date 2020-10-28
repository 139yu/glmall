package com.xj.glmall.product.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class SkuHasStockVo implements Serializable {
    private static final long serialVersionUID = -7258589170968344699L;
    private Long skuId;
    private Boolean hasStack;
}
