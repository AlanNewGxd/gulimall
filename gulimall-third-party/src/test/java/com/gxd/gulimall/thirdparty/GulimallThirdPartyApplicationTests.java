package com.gxd.gulimall.thirdparty;

import com.aliyun.oss.OSSClient;
import com.gxd.common.utils.HttpUtils;
import com.gxd.gulimall.thirdparty.component.SmsComponent;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class GulimallThirdPartyApplicationTests {

    @Resource
    OSSClient ossClient;
    @Resource
    SmsComponent smsComponent;

//    @Test
//    void testSms() {
//        smsComponent.sendCode("16602789607", "996655");
//    }

    @Test
    void contextLoads() {
    }

    @Test
    public void testUpload() throws FileNotFoundException {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
//        String endpoint = "oss-cn-zhangjiakou.aliyuncs.com";
        // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
//        String accessKeyId = "accessKeyId";
//        String accessKeySecret = "accessKeySecret";

        // 创建OSSClient实例。
//        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        //上传文件流。
//        InputStream inputStream = new FileInputStream("C:\\Users\\AlanNew\\Pictures\\test\\ceshi.jpg");
//        ossClient.putObject("gulimall-alannew", "333.jpg", inputStream);
//
//        // 关闭OSSClient。
//        ossClient.shutdown();
//        System.out.println("上传成功.");
    }

    @Test
    public void sendsms(){

//        String host = "https://gyytz.market.alicloudapi.com";
//        String path = "/sms/smsSend";
//        String method = "POST";
//        String appcode = "appcode";
//        Map<String, String> headers = new HashMap<String, String>();
//        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
//        headers.put("Authorization", "APPCODE " + appcode);
//        Map<String, String> querys = new HashMap<String, String>();
//        querys.put("mobile", "18001130501");
//        querys.put("param", "**code**:12345,**minute**:5");
//        querys.put("smsSignId", "2e65b1bb3d054466b82f0c9d125465e2");
//        querys.put("templateId", "908e94ccf08b4476ba6c876d13f084ad");
//        Map<String, String> bodys = new HashMap<String, String>();
//
//        try {
//            /**
//             * 重要提示如下:
//             * HttpUtils请从
//             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
//             * 下载
//             *
//             * 相应的依赖请参照
//             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
//             */
//            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
//            System.out.println(response.toString());
//            //获取response的body
//            //System.out.println(EntityUtils.toString(response.getEntity()));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    @Test
    public void dxsendsms(){
        String host = "http://172.16.20.30:31273";
        String path = "/tymh_interface_sms/random_code";
        String method = "GET";
        Map<String, String> headers = new HashMap<String, String>();
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("id", "18001130501");
        querys.put("type", "1");
        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
            System.out.println(response.toString());
            //获取response的body
            System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
