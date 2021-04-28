package com.gxd.gulimall.product;


import com.gxd.gulimall.product.entity.BrandEntity;
import com.gxd.gulimall.product.service.BrandService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@SpringBootTest
class GulimallProductApplicationTests {

    @Autowired
    BrandService brandService;

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
