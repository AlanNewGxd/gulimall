package com.gxd.gulimall.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.ArrayList;
import java.util.List;

/**
 * @author guxiaodong
 * @version 1.0
 * @title 网关配置类
 * @date 2021/5/6 17:08
 */
@Configuration
public class GulimallCorsConfiguration {

    @Bean //添加过滤器
    public CorsWebFilter corsWebFilter(){
        // 基于url跨域，选择reactive包下的
        UrlBasedCorsConfigurationSource source=new UrlBasedCorsConfigurationSource();
        // 跨域配置信息
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        // 允许跨域的头
        corsConfiguration.addAllowedHeader("*");
        // 允许跨域的请求方式
        corsConfiguration.addAllowedMethod("*");

        // 允许跨域的请求来源  高版本的使用
        List<String> allowedOriginPatterns = new ArrayList<String>();
        allowedOriginPatterns.add("*");
        corsConfiguration.setAllowedOriginPatterns(allowedOriginPatterns);
//      corsConfiguration.addAllowedOrigin("*");  适用于低版本的spring-boot-starter-parent

        // 是否允许携带cookie跨域
        corsConfiguration.setAllowCredentials(true);
        // 任意url都要进行跨域配置
        source.registerCorsConfiguration("/**",corsConfiguration);
        return new CorsWebFilter(source);
    }
}

