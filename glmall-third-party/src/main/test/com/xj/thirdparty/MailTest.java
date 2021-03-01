package com.xj.thirdparty;

import com.xj.glmall.thirdparty.GlmallThirdPartyApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

@SpringBootTest(classes = GlmallThirdPartyApplication.class)
@RunWith(SpringRunner.class)
public class MailTest {
    @Autowired
    private JavaMailSender mailSender;

    @Test
    public void test1(){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("1395759132@qq.com");
        mailMessage.setTo("1395759132@qq.com");
        mailMessage.setSubject("感谢您注册");
        mailMessage.setText("你的注册码为：123456");
        mailSender.send(mailMessage);
    }

    @Test
    public void uuidTest(){
        System.out.println(UUID.randomUUID().toString());
        System.out.println(UUID.randomUUID().toString());
        System.out.println(UUID.randomUUID().toString());
        System.out.println(UUID.randomUUID().toString());
    }
}
