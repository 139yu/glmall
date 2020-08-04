package com.xj.glmall.order;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@MapperScan("com.xj.glmall.order.dao")
@EnableDiscoveryClient
public class GlmallOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(GlmallOrderApplication.class, args);
    }

}
