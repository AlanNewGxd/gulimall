package com.gxd.gulimall.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
/*
 * 开启spring cach缓存
 * 1、整合依赖
   2、配置：CacheAutoConfiguration，导入了各种缓存的配置：mappings.put(CacheType.REDIS, RedisCacheConfiguration.class)
	1）RedisCacheConfiguration.class
		注入了：@Bean：RedisCacheManager
	2）application.properties配置：
		spring.cache.type=redis
	3）开启缓存@EnableCaching
   3、存储同一类型的数据，都指定为同一个分区@Cacheable(value={"category"})【实现失效模式+双写模式】
   4、不要指定key的前缀
        @Cacheable：标注方法上：当前方法的结果存入缓存，如果缓存中有，方法不调用
		@CacheEvict：触发将数据从缓存删除的操作
		@CachePut：不影响方法执行更新缓存
		@Caching：组合以上多个操作
		@CacheConfig：在类级别共享缓存的相同配置
 **/
//@EnableCaching  写到配置类了
@EnableFeignClients(basePackages="com.gxd.gulimall.product.feign") //扫描接口方法注解
@EnableDiscoveryClient
@MapperScan("com.gxd.gulimall.product.dao")
@SpringBootApplication
public class GulimallProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(GulimallProductApplication.class, args);
    }

}
