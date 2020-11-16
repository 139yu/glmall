package com.xj.glmall.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@MapperScan("com.xj.glmall.product.dao")
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.xj.glmall.product.feign")
public class    GlmallProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(GlmallProductApplication.class, args);
    }

}
