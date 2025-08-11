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
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
/*
 * Central Spring Configuration class that define infratsturre realted beans for
 * authgurad-app-service
 * <p>Provide Configuration : </p>
 * <ul>
 * <li>{@link ModelMapper} bean for DTO conversion</li>
 * <li>{@link ChacheMange} provide bean for redis cache manager</li>
 * <li>{@link SecurityFilterChain} bean for spring security configuration currntly disable cors configuratin and permit all request</li>
 * </ul>
 * 
 */
@Configuration
public class config {

    /*
     * Provides a ModelMapper bean for object-to-object mapping between entities and DTOs
     * @return new modelMapper Instance
     */
    @Bean
    public ModelMapper getModelMapper() {
        return new ModelMapper();
    }
    /*
     * Provide a configured redis {@link CacheManager} 
     * @param RedisConnectionFactory connectionFactory
     * @return the configue cache manager
     * 
     */
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
    /**
     * Configures Spring Security to disable CORS and CSRF protection,
     * and permit all incoming HTTP requests without authentication.
     *
     * @param httpSecurity the HttpSecurity object to configure
     * @return the configured SecurityFilterChain
     * @throws Exception if an error occurs during configuration
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.cors(cors -> cors.disable())
                .csrf(crsf -> crsf.disable())
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
        return httpSecurity.build();
    }

}
