package com.xj.glmall.product.vo;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class SpuItemBaseAttrVo {
    private String groupName;
    private List<SpuBaseAttrVo> attrs;
}