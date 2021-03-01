package com.xj.glmall.product.config;

import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;

@Configuration
public class GlmallSessionConfig {

    /**
     * 设置cookie作用域
     * @return
     */
    @Bean
    public CookieSerializer cookieSerializer(){
        DefaultCookieSerializer serializer = new DefaultCookieSerializer();
        serializer.setDomainName("glmall.com");
        serializer.setCookieName("GLMALLSESSION");
        return serializer;
    }
    @Bean
    public RedisSerializer<Object> redisSerializer(){
        return new GenericFastJsonRedisSerializer();
    }



}
