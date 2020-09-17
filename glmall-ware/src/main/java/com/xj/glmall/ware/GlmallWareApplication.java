package com.xj.glmall.ware;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@MapperScan("com.xj.glmall.ware.dao")
@EnableFeignClients(basePackages = "com.xj.glmall.ware.feign")
public class GlmallWareApplication {

    public static void main(String[] args) {
        SpringApplication.run(GlmallWareApplication.class, args);
    }

}
