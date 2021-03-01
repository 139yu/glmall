package com.xj.glmall.search.service;

import com.xj.glmall.search.vo.SearchParam;
import com.xj.glmall.search.vo.SearchResult;

import java.io.IOException;

public interface MallSearchService {
    SearchResult listPage(SearchParam searchParam) throws IOException;
}
