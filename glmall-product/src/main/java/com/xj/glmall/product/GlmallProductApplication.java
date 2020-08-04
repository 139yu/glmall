package com.xj.glmall.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@MapperScan("com.xj.glmall.product.dao")
@EnableDiscoveryClient
public class GlmallProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(GlmallProductApplication.class, args);
    }

}
