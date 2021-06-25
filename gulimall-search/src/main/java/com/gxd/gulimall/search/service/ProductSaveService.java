package com.gxd.gulimall.search.service;

import com.gxd.common.to.es.SkuEsModel;

import java.io.IOException;
import java.util.List;

/**
 * @Description ProductSaveService
 * @Param
 * @Author guxiaodong
 * @Date 17:19 2021/6/25
 **/
public interface ProductSaveService {

    boolean productStatusUp(List<SkuEsModel> skuEsModels) throws IOException;
}

