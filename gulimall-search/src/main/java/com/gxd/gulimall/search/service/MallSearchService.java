package com.gxd.gulimall.search.service;

import com.gxd.gulimall.search.vo.SearchParam;
import com.gxd.gulimall.search.vo.SearchResult;

public interface MallSearchService {
    SearchResult search(SearchParam param);
}
