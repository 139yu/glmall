package com.xj.glmall.product.feign;

import com.xj.glmall.common.to.BoundsTo;
import com.xj.glmall.common.to.SkuReductionTo;
import com.xj.glmall.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("glmall-coupon")
public interface CouponFeignService {

    @PostMapping("/coupon/spubounds/save")
    R saveSpuBounds(@RequestBody BoundsTo boundsTo);
    @PostMapping("/coupon/skufullreduction/saveReductionInfo")
    R saveReductionInfo(@RequestBody SkuReductionTo skuReductionTo);
}
