package com.gxd.gulimall.member;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 想要远程调用服务的步骤
 * 1.pom引入openfeign
 * 2.编写一个接口，接口告诉springcloud这个接口需要调用远程服务
 *     2.1在接口里声明feignClient("gulimall-coupon")他是一个远程调用客户端，然后调用feign的coupon服务
 *     2.2要调用/coupon/coupon/member/couponsList方法
 * 3.开启远程调用服务功能@EnableFeignClients，需要指定调用远程服务的基础包
 **/
@EnableFeignClients(basePackages="com.gxd.gulimall.member.feign") //扫描接口方法注解
@EnableDiscoveryClient
@MapperScan("com.gxd.gulimall.member.dao")
@SpringBootApplication
public class GulimallMemberApplication {

    public static void main(String[] args) {
        SpringApplication.run(GulimallMemberApplication.class, args);
    }

}
