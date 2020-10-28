package com.xj.glmall.ware.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class SkuHasStockVo implements Serializable {
    private static final long serialVersionUID = 5581532862269844437L;
    private Long skuId;
    private Boolean hasStack;
}
