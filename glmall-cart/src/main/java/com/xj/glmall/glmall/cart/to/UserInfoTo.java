package com.xj.glmall.glmall.cart.to;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserInfoTo {
    private String userId;
    private String userKey;
    private Boolean tempUser = false;//是否是临时用户
    private Boolean first = true;//是否第一次访问
}
