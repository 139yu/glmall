package com.xj.glmall.product.feign;

import com.xj.glmall.common.utils.R;
import com.xj.glmall.product.vo.SkuHasStockVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient("glmall-ware")
public interface WareFeignService {

    @GetMapping("/ware/waresku/getSkuStock/{skuId}")
    R getSkuStock(@PathVariable Long skuId);

    @PostMapping("/ware/waresku/getSkuHasStock")
    R<List<SkuHasStockVo>> getSkuHasStock(@RequestBody List<Long> skuIds);
}
