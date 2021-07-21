package com.gxd.gulimall.product.config;

import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@EnableConfigurationProperties(CacheProperties.class)
@EnableCaching
@Configuration
public class MyCacheConfig {

//    @Autowired
//    CacheProperties cacheProperties;
    /*
     * 1、读模式:
	缓存穿透:查询一个DB不存在的数据。解决:缓存空数据;ache-null-values=true【布隆过滤器】
	缓存击穿:大量并发进来同时查询一个正好过期的数据。解决:加锁; 默认未加锁【sync = true】本地锁
	缓存雪崩:大量的key同时过期。解决:加上过期时间。: spring.cache.redis.time-to-live= 360000s
        2、写模式:（缓存与数据库一致)（没有解决）
            1)、读写加锁。
            2)、引入canal，感知mysql的更新去更新缓存
            3)、读多写多，直接去查询数据库就行
    总结:
        常规数据（读多写少，即时性，一致性要求不高的数据）﹔完全可以使用Spring-Cache，写模式(只要缓存的数据有过期时间就可以）
        特殊数据:特殊设计
     **/

    /**
     * 需要将配置文件中的配置设置上
     * 1、使配置类生效
     * 1）开启配置类与属性绑定功能EnableConfigurationProperties
     *
     * @ConfigurationProperties(prefix = "spring.cache")  public class CacheProperties
     * 2）注入就可以使用了
     * @Autowired CacheProperties cacheProperties;
     * 3）直接在方法参数上加入属性参数redisCacheConfiguration(CacheProperties redisProperties)
     * 自动从IOC容器中找
     * <p>
     * 2、给config设置上
     */
    @Bean
    RedisCacheConfiguration redisCacheConfiguration(CacheProperties cacheProperties) {
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();
        config = config.serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()));
        config = config.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
        // 配置文件生效：RedisCacheConfiguration
        CacheProperties.Redis redisProperties = cacheProperties.getRedis();
        if (redisProperties.getTimeToLive() != null) {
            config = config.entryTtl(redisProperties.getTimeToLive());
        }
        if (redisProperties.getKeyPrefix() != null) {
            config = config.prefixCacheNameWith(redisProperties.getKeyPrefix());
        }
        if (!redisProperties.isCacheNullValues()) {
            config = config.disableCachingNullValues();
        }
        if (!redisProperties.isUseKeyPrefix()) {
            config = config.disableKeyPrefix();
        }
        return config;
    }
}
