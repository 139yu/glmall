package com.xj.glmall.common.exception;

public enum BizCodeEnum {
    /**
     * 10：通用
     * 11：商品
     * 12：订单
     * 13：购物车
     * 14：物流
     * 15：用户
     */
    UNKONW_EXCEPTION(10000,"系统未知异常"),
    VALID_EXCEPTION(10001,"参数校验失败"),
    SMS_CODE_EXCEPTION(10002,"发送验证码次数过于频繁"),
    PRODUCT_UP_ERRPR(11000,"商品上架失败"),
    USER_EXIST_EXCEPTION(15001,"用户已存在"),
    EMAIL_EXIST_EXCEPTION(15002,"邮箱已存在"),
    LOGIN_PASSWORD_OR_USERNAME_INVALID_EXCEPTION(15003,"用户名或密码错误"),
    LOGIN_FAILED(15004,"登录失败"),
    ;

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
