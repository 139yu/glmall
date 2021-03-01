package com.xj.glmall.product.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class AttrRespVo extends AttrVo implements Serializable {
    /**
     * 			"catalogName": "手机/数码/手机", //所属分类名字
     * 			"groupName": "主体", //所属分组名字
     */
    private String catalogName;
    private String groupName;

    private Long[] catalogPath;
}
