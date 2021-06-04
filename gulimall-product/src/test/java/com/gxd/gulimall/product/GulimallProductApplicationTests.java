package com.gxd.gulimall.product;


import com.gxd.gulimall.product.entity.BrandEntity;
import com.gxd.gulimall.product.service.BrandService;
import com.gxd.gulimall.product.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Slf4j
@SpringBootTest
class GulimallProductApplicationTests {

    @Autowired
    BrandService brandService;

    @Autowired
    CategoryService categoryService;

    @Test
    public void findpaths(){
        Long[] catelogIds = categoryService.findCateLogPath(225L);
        log.info("完整路径：{}",Arrays.asList(catelogIds));
    }

    @Test
    public void testUpload(){

        // yourEndpoint填写Bucket所在地域对应的Endpoint。以华东1（杭州）为例，Endpoint填写为https://oss-cn-hangzhou.aliyuncs.com。
//        String endpoint = "oss-cn-zhangjiakou.aliyuncs.com";
        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
//        String accessKeyId = "LTAI5tJiMCkdzm88Q38B5uFT";
//        String accessKeySecret = "6v6GDQQDqDJ2ATo1OdWsdtpro3zg5C";

        // 创建OSSClient实例。
//        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 创建PutObjectRequest对象。
        // 填写Bucket名称、Object完整路径和本地文件的完整路径。Object完整路径中不能包含Bucket名称。
        // 如果未指定本地路径，则默认从示例程序所属项目对应本地路径中上传文件。
//        PutObjectRequest putObjectRequest = new PutObjectRequest("gulimall-alannew", "ceshi.jpg", new File("C:\\Users\\AlanNew\\Pictures\\test\\ceshi.jpg"));

        // 如果需要上传时设置存储类型和访问权限，请参考以下示例代码。
        // ObjectMetadata metadata = new ObjectMetadata();
        // metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
        // metadata.setObjectAcl(CannedAccessControlList.Private);
        // putObjectRequest.setMetadata(metadata);

        // 上传文件。
//        ossClient.putObject(putObjectRequest);

        // 关闭OSSClient。
//        ossClient.shutdown();

        System.out.println("上传成功。。。");

    }

    @Test
    void contextLoads() {
        BrandEntity brandEntity = new BrandEntity();
//        brandEntity.setName("苹果");
//        brandEntity.setDescript("真好吃");
//        brandService.save(brandEntity);
//        System.out.println("保存成功。。。");


        //修改
//        brandEntity.setBrandId(6L);
//        brandEntity.setName("360");
//        brandEntity.setDescript("安全");
//        brandService.updateById(brandEntity);
//        System.out.println("修改成功。。。");

        //查询
//        List<BrandEntity> list = brandService.list();
//        for (int i = 0; i <list.size() ; i++) {
//            System.out.println("查询结果："+list.get(i));
//        }
//
//        R.add("list",list);
//
//        System.out.println();
//        System.out.println(R.ok().put("result",list));

//        Map<String, Object> params = new HashMap<String, Object>();
//        params.put("page","1");
//        params.put("limit","10");
//        PageUtils pageUtils = brandService.queryPage(params);
//        System.out.println(R.ok().put("list",pageUtils));

        List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);
        // 获取对应的平方数
        List<Integer> squaresList =
                numbers.stream()
                        .map( i -> i*i)
                        .distinct()
                        .collect(Collectors.toList());

        squaresList.forEach(System.out::println);

        Random random = new Random();
        random.ints().limit(10).sorted().forEach(System.out::println);

        List<String>strings = Arrays.asList("abc", "", "bc", "efg", "abcd","", "jkl");
        List<String> filtered = strings.stream().filter(string -> !string.isEmpty()).collect(Collectors.toList());

        System.out.println("筛选列表: " + filtered);
        String mergedString = strings.stream().filter(string -> !string.isEmpty()).collect(Collectors.joining(", "));
        System.out.println("合并字符串: " + mergedString);
    }
}
