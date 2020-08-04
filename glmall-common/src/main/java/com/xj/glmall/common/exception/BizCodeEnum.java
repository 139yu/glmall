package com.xj.glmall.common.exception;

public enum BizCodeEnum {
    UNKONW_EXCEPTION(10000,"系统未知异常"),
    VALID_EXCEPTION(10001,"参数校验失败");

    private Integer code;
    private String message;
    BizCodeEnum(int code,String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
