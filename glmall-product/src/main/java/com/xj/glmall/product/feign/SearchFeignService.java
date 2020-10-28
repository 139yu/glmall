package com.xj.glmall.product.feign;

import com.xj.glmall.common.to.es.SkuEsModel;
import com.xj.glmall.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient("glmall-search")
public interface SearchFeignService {

    @PostMapping("/search/save/product")
    public R productSave(@RequestBody List<SkuEsModel> skuEsModelList);
}
