package com.xj.glmall.product.web;

import com.xj.glmall.product.entity.CategoryEntity;
import com.xj.glmall.product.service.CategoryService;
import com.xj.glmall.product.vo.Catalog2Vo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@Controller
public class IndexController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping({"/","/index.html"})
    public String indexPage(Model model){
        List<CategoryEntity> categoryEntities = categoryService.getLevel1Category();
        model.addAttribute("categories",categoryEntities);
        return "index";
    }

    @GetMapping("index/json/catalog.json")
    public Map<String ,List<Catalog2Vo>> getCatalogJson(){
        Map<String, List<Catalog2Vo>> catalogMap = categoryService.getCatalogMap();
        return catalogMap;
    }
}
