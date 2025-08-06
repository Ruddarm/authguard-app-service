package com.authguard.authguard_app_service.config;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;

@Configuration
public class config {

    @Bean
    public ModelMapper getModelMapper() {
        return new ModelMapper();
    }

    @Bean
    public CacheManager redisChacheMangaer(RedisConnectionFactory connectionFatory) {
        Map<String, RedisCacheConfiguration> cacheMangaer = new HashMap<>();
        cacheMangaer.put("appListByUser",
                RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(10)).enableTimeToIdle());
                cacheMangaer.put("appById",
                RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(10)).enableTimeToIdle());
        return RedisCacheManager.builder(RedisCacheWriter.nonLockingRedisCacheWriter(connectionFatory))
                .cacheDefaults(RedisCacheConfiguration.defaultCacheConfig())
                .withInitialCacheConfigurations(cacheMangaer).build();
    }
    
}
