package com.xj.glmall.product.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Catalog2Vo implements Serializable {
    private String catalog1Id;
    private String id;
    private String name;
    private List<Catalog3Vo> catalog3List;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Catalog3Vo implements Serializable{
        private String catalog2Id;
        private String id;
        private String name;
    }
}
