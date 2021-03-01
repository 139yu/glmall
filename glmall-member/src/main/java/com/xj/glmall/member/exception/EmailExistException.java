package com.xj.glmall.member.exception;

public class EmailExistException extends Exception {
    public EmailExistException(){
        super("邮箱存在");
    }
}
