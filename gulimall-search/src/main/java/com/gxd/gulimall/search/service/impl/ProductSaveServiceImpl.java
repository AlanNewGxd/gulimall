package com.gxd.gulimall.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.gxd.common.constant.EsConstant.EsConstant;
import com.gxd.common.to.es.SkuEsModel;
import com.gxd.gulimall.search.config.GuliESConfig;
import com.gxd.gulimall.search.service.ProductSaveService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author guxiaodong
 * @version 1.0
 * @title TODO
 * @date 2021/6/25 17:22
 */
@Slf4j
@Service("productSaveService")
public class ProductSaveServiceImpl implements ProductSaveService {

    @Resource
    RestHighLevelClient client;

    /**
     * 保存数据到es：
     * 1、创建单个保存请求
     *      IndexRequest indexRequest = new IndexRequest(EsConstant.PRODUCT_INDEX)
     * 2、请求绑定数据
     *      indexRequest.source(JSON.toJSONString(model), XContentType.JSON)
     * 3、循环构建批量保存请求
     *      BulkRequest.add(indexRequest)
     * 4、使用客户端发送批量保存请求
     *      client.bulk(bulkRequest, GulimallElasticSearch.COMMON_OPTIONS)
     */
    @Override
    public boolean productStatusUp(List<SkuEsModel> skuEsModels) throws IOException {
        // 保存到ES
        // 1、给es中建立索引、product，建立映射关系

        // 2、给es中保存这些数据【使用bulk，不使用index一条条保存】
        // BulkRequest bulkRequest, RequestOptions options
        BulkRequest bulkRequest = new BulkRequest();
        for (SkuEsModel model : skuEsModels) {
            // 构造保存请求，设置索引+id
            IndexRequest indexRequest = new IndexRequest(EsConstant.PRODUCT_INDEX);
            indexRequest.id(model.getSkuId().toString());
            String s = JSON.toJSONString(model);
            // 绑定请求与数据
            indexRequest.source(s, XContentType.JSON);
            // 添加到批量保存
            bulkRequest.add(indexRequest);
        }
        BulkResponse bulk = client.bulk(bulkRequest, GuliESConfig.COMMON_OPTIONS);
        // TODO 如果批量错误
        boolean b = bulk.hasFailures();
        List<String> collect = Arrays.stream(bulk.getItems()).map(item -> {
            return item.getId();
        }).collect(Collectors.toList());
        log.info("商品上架完成：{},返回数据：{}", collect, bulk);

        return !b;// 因为b代表的是是否有异常
    }
}
