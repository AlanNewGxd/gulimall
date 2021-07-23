package com.gxd.gulimall.search;

import com.alibaba.fastjson.JSON;
import com.gxd.gulimall.search.config.GuliESConfig;
import lombok.Data;
import lombok.ToString;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.AvgAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.IOException;

@SpringBootTest
public class GulimallSearchApplicationTests {

    @Resource
    RestHighLevelClient client;

    @Test
    public void qwe() {
        System.out.println("测试");
    }

    @Test
    public void contextLoads() {
        System.out.println(client);
    }

    /**
     * @Description 存储数据到es,id不变为更新操作，版本＋1
     * @Param []
     * @Author guxiaodong
     * @Date 16:43 2021/5/27
     **/
    @Test
    public void indexData() throws IOException {

        // 设置索引
        IndexRequest indexRequest = new IndexRequest ("users");
        indexRequest.id("1");

        User user = new User();
        user.setUserName("张三");
        user.setAge(20);
        user.setGender("男");
        String jsonString = JSON.toJSONString(user);

        //设置要保存的内容，指定数据和类型
        indexRequest.source(jsonString, XContentType.JSON);

        //执行创建索引和保存数据
        IndexResponse index = client.index(indexRequest, GuliESConfig.COMMON_OPTIONS);

        System.out.println(index);

    }

    /*
     * @Description 查看
     * @Param []
     * @Author guxiaodong
     * @Date 9:43 2021/5/28
     **/
    @Test
    public void find() throws IOException {
        // 1 创建检索请求
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("bank");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        // 构造检索条件
//        sourceBuilder.query();
//        sourceBuilder.from();
//        sourceBuilder.size();
//        sourceBuilder.aggregation();
        sourceBuilder.query(QueryBuilders.matchQuery("address","mill"));
        System.out.println("检索条件"+sourceBuilder.toString());

        searchRequest.source(sourceBuilder);

        // 2 执行检索
        SearchResponse response = client.search(searchRequest, GuliESConfig.COMMON_OPTIONS);
        // 3 分析响应结果
        System.out.println("检索结果"+response.toString());
    }

    @Test
    public void find2() throws IOException {
        // 1 创建检索请求
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("bank");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        // 构造检索条件
//        sourceBuilder.query();
//        sourceBuilder.from();
//        sourceBuilder.size();
//        sourceBuilder.aggregation();
        sourceBuilder.query(QueryBuilders.matchQuery("address","mill"));
        //AggregationBuilders工具类构建AggregationBuilder
        // 构建第一个聚合条件:按照年龄的值分布
        TermsAggregationBuilder agg1 = AggregationBuilders.terms("agg1").field("age").size(10);// 聚合名称
        // 参数为AggregationBuilder
        sourceBuilder.aggregation(agg1);
        // 构建第二个聚合条件:平均薪资
        AvgAggregationBuilder agg2 = AggregationBuilders.avg("agg2").field("balance");
        sourceBuilder.aggregation(agg2);

        System.out.println("检索条件"+sourceBuilder.toString());

        searchRequest.source(sourceBuilder);

        // 2 执行检索
        SearchResponse response = client.search(searchRequest, GuliESConfig.COMMON_OPTIONS);
        // 3 分析响应结果
        System.out.println(response.toString());

        // 3.1 获取java bean
        SearchHits hits = response.getHits();
        SearchHit[] hits1 = hits.getHits();
        for (SearchHit hit : hits1) {
            hit.getId();
            hit.getIndex();
            String sourceAsString = hit.getSourceAsString();
            Account account = JSON.parseObject(sourceAsString, Account.class);
            System.out.println(account);
        }

        // 3.2 获取检索到的分析信息
        Aggregations aggregations = response.getAggregations();
        Terms agg21 = aggregations.get("agg2");
        for (Terms.Bucket bucket : agg21.getBuckets()) {
            String keyAsString = bucket.getKeyAsString();
            System.out.println(keyAsString);
        }

    }

    /**
    *           "account_number" : 1,
    *           "balance" : 39225,
    *           "firstname" : "Amber",
    *           "lastname" : "Duke",
    *           "age" : 32,
    *           "gender" : "M",
    *           "address" : "880 Holmes Lane",
    *           "employer" : "Pyrami",
    *           "email" : "amberduke@pyrami.com",
    *           "city" : "Brogan",
    *           "state" : "IL"
     **/
    @ToString
    @Data
    class Account{
        private Integer account_number;
        private Double balance;
        private String firstname;
        private String lastname;
        private Integer age;
        private String gender;
        private String address;
        private String employer;
        private String email;
        private String city;
        private String state;
    }

}
