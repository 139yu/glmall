package com.xj.glmall.common.to;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BoundsTo {
    private BigDecimal buyBounds;
    private BigDecimal growBounds;
    private Long spuId;
}
