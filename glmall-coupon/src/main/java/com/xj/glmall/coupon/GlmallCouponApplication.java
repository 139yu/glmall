package com.xj.glmall.coupon;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@MapperScan("com.xj.glmall.coupon.dao")
@EnableDiscoveryClient
public class GlmallCouponApplication {

    public static void main(String[] args) {
        SpringApplication.run(GlmallCouponApplication.class, args);
    }

}
