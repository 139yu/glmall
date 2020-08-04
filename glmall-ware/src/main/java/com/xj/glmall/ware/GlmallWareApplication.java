package com.xj.glmall.ware;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.xj.glmall.ware.dao")
public class GlmallWareApplication {

    public static void main(String[] args) {
        SpringApplication.run(GlmallWareApplication.class, args);
    }

}
