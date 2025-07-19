package com.ecommerce.productservice.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests(authorize -> authorize
//                                .requestMatchers("/products/health").permitAll()
//                                .requestMatchers("/products/get/**").permitAll()
//                                .anyRequest().authenticated()

    /// /                        .requestMatchers("/products/**").authenticated()
    /// /                        .requestMatchers("/categories/**").authenticated()
    /// /                        //.authenticated()
    /// /                        .hasAuthority("SCOPE_ADMIN")
//                        //.anyRequest().permitAll()
//                )
//                .oauth2ResourceServer((oauth2) -> oauth2.jwt(Customizer.withDefaults()));
//        return http.build();
//    }
    @Bean
    public SecurityFilterChain publicChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/products/health", "/products/get/**")
                .authorizeHttpRequests(authz -> authz.anyRequest().permitAll());
        return http.build();
    }

    @Bean
    public SecurityFilterChain securedChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authz -> authz.anyRequest().authenticated())
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));
        return http.build();
    }


}