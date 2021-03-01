package com.xj.glmall.search.controller;

import com.alibaba.fastjson.JSON;
import com.xj.glmall.search.service.MallSearchService;
import com.xj.glmall.search.vo.SearchParam;
import com.xj.glmall.search.vo.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
public class SearchController {

    @Autowired
    private MallSearchService mallSearchService;

    @GetMapping("list.html")
    public String listPage(SearchParam param, Model model, HttpServletRequest request) throws IOException {
        String queryString = request.getQueryString();
        param.set_queryString(queryString);
        SearchResult searchResult = mallSearchService.listPage(param);
        model.addAttribute("result",searchResult);
        System.out.println(JSON.toJSON(searchResult));
        return "list";
    }
}
