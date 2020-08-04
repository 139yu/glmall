package com.xj.glmall.member;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@MapperScan("com.xj.glmall.member.dao")
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.xj.glmall.member.client")
@RefreshScope
public class GlmallMemberApplication {
    public static void main(String[] args) {
        SpringApplication.run(GlmallMemberApplication.class, args);
    }

}
