package com.authguard.authguard_app_service.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.authguard.authguard_app_service.Interceptors.AuthInterceptors;
/*
 * Spring web Mvc configuration class for regsitering application wide interceptors
 * <p>
 * This configure register {@link AuhtInerecpetors} to intercept 
 * incomming request to check for authentication and authroization 
 * before they reach controller layer.
 * </p> 
 * 
 * <p>
 * Intercepter are execute before and after controller method call , it use 
 * for preprocess of incoming http request such as authentication and modification 
 * of resposne.
 * </p>
 * 
 */
@Configuration
public class WebConfig implements WebMvcConfigurer  {
    @Autowired
    private AuthInterceptors authInterceptors;

    /*
     * 
     * Register custom authintecepator for authentication in regsitry
     * 
     * @param registry the {@link InterceptorRegistry} used to register interceptors
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptors);
    }
}
