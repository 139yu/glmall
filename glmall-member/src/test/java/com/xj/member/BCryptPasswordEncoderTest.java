package com.xj.member;

import com.xj.glmall.member.GlmallMemberApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = GlmallMemberApplication.class)
public class BCryptPasswordEncoderTest {

    @Test
    public void test1(){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encode = bCryptPasswordEncoder.encode("123456");
        System.out.println(bCryptPasswordEncoder.matches("123456", encode));
    }
}
