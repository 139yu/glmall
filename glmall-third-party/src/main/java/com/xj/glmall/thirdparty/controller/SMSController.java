package com.xj.glmall.thirdparty.controller;

import com.xj.glmall.common.constant.AuthServerConstant;
import com.xj.glmall.common.exception.BizCodeEnum;
import com.xj.glmall.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sms")
public class SMSController {
    @Autowired
    private JavaMailSender mailSender;

    @GetMapping("/sendCode")
    public R sendCode(@RequestParam("email") String email, @RequestParam("code") String code) {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom("1395759132@qq.com");
            mailMessage.setTo(email);
            mailMessage.setSubject("感谢使用");
            mailMessage.setText("您的验证码为：" + code);
            mailSender.send(mailMessage);
            return R.ok();
    }
}
