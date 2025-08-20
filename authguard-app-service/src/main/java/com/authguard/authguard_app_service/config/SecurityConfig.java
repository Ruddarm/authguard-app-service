package com.authguard.authguard_app_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.authguard.authguard_app_service.filters.ServiceJwtAuthFilter;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig {
    private final ServiceJwtAuthFilter serviceJwtAuthFilter;

    private HttpSecurity disableCsrfAndCors(HttpSecurity http) throws Exception {
        return http.cors(cors -> cors.disable())
                .csrf(csrf -> csrf.disable());
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
    @Order(2) // Inter-service first
    public SecurityFilterChain interServiceFilterChain(HttpSecurity http) throws Exception {
        disableCsrfAndCors(http)
                .securityMatcher("/service/**")
                .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
                .addFilterBefore(serviceJwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
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
    @Order(1) // Global user-facing chain
    public SecurityFilterChain globalFilterChain(HttpSecurity http) throws Exception {
        disableCsrfAndCors(http)
                .securityMatcher("/apps/**")
                .authorizeHttpRequests(auth -> auth.requestMatchers("/apps/**").permitAll());
        return http.build();
    }

}
