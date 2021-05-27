package com.gxd.gulimall.search;

import com.alibaba.fastjson.JSON;
import com.gxd.gulimall.search.config.GuliESConfig;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GulimallSearchApplicationTests {

    @Autowired
    RestHighLevelClient client;

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


}
