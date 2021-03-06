package com.xj.glmall.member.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
public class UserRegisterVo {
    @NotEmpty(message = "用户名必须提交")
    @Length(min = 4, max = 16, message = "用户名必须是8-16位字符")
    private String username;

    @NotEmpty(message = "密码必须填写")
    @Length(min = 8, max = 16, message = "密码必须是8-16位字符")
    private String password;

    @Pattern(regexp = "/^[A-Za-z0-9]+([_\\.][A-Za-z0-9]+)*@([A-Za-z0-9\\-]+\\.)+[A-Za-z]{2,6}$/", message = "邮箱格式不正确")
    private String email;

    @NotEmpty(message = "验证码必须填写")
    private String code;
}