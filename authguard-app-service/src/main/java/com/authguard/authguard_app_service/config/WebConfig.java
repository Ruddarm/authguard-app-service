package com.authguard.authguard_app_service.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.authguard.authguard_app_service.Interceptors.AuthInterceptors;

@Configuration
public class WebConfig implements WebMvcConfigurer  {
    @Autowired
    private AuthInterceptors authInterceptors;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptors);
    }
}
