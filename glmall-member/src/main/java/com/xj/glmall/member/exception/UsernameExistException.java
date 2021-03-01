package com.xj.glmall.member.exception;

public class UsernameExistException extends Exception {
    public UsernameExistException(){
        super("用户名已存在");
    }
}
