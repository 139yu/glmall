package com.xj.glmall.auth.feign;

import com.xj.glmall.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("glmall-third-party")
public interface ThirdPartyFeignClient {

    @GetMapping("/sms/sendCode")
    public R sendCode(@RequestParam("email") String email, @RequestParam("code") String code);
}
