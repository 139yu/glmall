package com.xj.glmall.glmall.cart.feign;

import com.xj.glmall.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("glmall-product")
public interface ProductFeignService {
    @GetMapping("product/skuinfo/info/{skuId}")
    public R info(@PathVariable("skuId") Long skuId);

    @GetMapping("product/skusaleattrvalue/listStringSaleAttr")
    public List<String> listStringSaleAttr(@RequestParam("skuId") Long skuId);
}
