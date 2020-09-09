package com.xj.glmall.product.vo;

import com.xj.glmall.product.entity.AttrEntity;
import com.xj.glmall.product.entity.AttrGroupEntity;
import lombok.Data;

import java.util.List;

@Data
public class AttrGroupVo extends AttrGroupEntity {
    private List<AttrEntity> attrs;
}
