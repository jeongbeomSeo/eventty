package com.eventty.applyservice.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class BasicSecurityConfig {
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){return new BCryptPasswordEncoder(); }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(authorizationConfig -> {
                    authorizationConfig
                            .anyRequest()
                            .permitAll();
                })
                .csrf(csrfconfig -> {
                    csrfconfig
                            .disable();
                })
                .headers(headers -> headers
                        .frameOptions(frameOptions -> frameOptions
                                .disable()) // X-Frame-Options 비활성화
                )
                .build();
    }
}