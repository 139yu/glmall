package com.xj.glmall.product.feign;

import com.xj.glmall.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("glmall-ware")
public interface WareFeignService {

    @GetMapping("/ware/waresku/getSkuStock/{skuId}")
    R getSkuStock(@PathVariable Long skuId);
}
