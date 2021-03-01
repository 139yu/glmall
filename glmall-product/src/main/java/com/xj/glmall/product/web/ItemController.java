package com.xj.glmall.product.web;

import com.alibaba.fastjson.JSON;
import com.xj.glmall.product.service.SkuInfoService;
import com.xj.glmall.product.vo.SkuItemVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.concurrent.ExecutionException;

@Controller
public class ItemController {

    @Autowired
    private SkuInfoService skuInfoService;

    @GetMapping("/{skuId}.html")
    public String skuItem(@PathVariable("skuId") Long skuId, Model model) throws ExecutionException, InterruptedException {
        SkuItemVo skuItemVo = skuInfoService.getSkuItem(skuId);

        System.out.println(JSON.toJSONString(skuItemVo));
        model.addAttribute("item",skuItemVo);
        return "item";
    }
}
