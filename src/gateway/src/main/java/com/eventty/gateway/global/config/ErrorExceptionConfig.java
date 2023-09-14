package com.eventty.gateway.global.config;

import com.eventty.gateway.global.exception.JwtTokenExceptionHandler;
import com.eventty.gateway.global.exception.utils.DataErrorLogger;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ErrorExceptionConfig {

    private final ObjectMapper objectMapper;
    private final DataErrorLogger dataErrorLogger;

    @Bean
    public ErrorWebExceptionHandler globalExceptionHandler() {
        return new JwtTokenExceptionHandler(objectMapper, dataErrorLogger);
    }

}
