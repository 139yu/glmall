package com.xj.glmall.search.feign;

import com.xj.glmall.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient("glmall-product")
public interface ProductFeignClient {
    @GetMapping("/product/attr/info/{attrId}")
    public R info(@PathVariable("attrId") Long attrId);

    @PostMapping("product/brand/info/listBrands")
    public R listBrands(@RequestBody List<Long> brandIds);
}
