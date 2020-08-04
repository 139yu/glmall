package com.xj.glmall.member.client;

import com.xj.glmall.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "glmall-coupon")
public interface CouponFeignService {

    @GetMapping("coupon/coupon/info/{id}")
    public R info(@PathVariable("id") Long id);
}
