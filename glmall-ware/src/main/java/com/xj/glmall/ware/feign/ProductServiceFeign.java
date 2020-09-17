package com.xj.glmall.ware.feign;

import com.xj.glmall.common.to.SkuInfoTo;
import com.xj.glmall.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("glmall-product")
public interface ProductServiceFeign {

    @GetMapping("/product/skuinfo/info/{skuId}")
    R getSkuInfo(@PathVariable("skuId")Long skuId);
}
