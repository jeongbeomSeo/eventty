package com.eventty.authservice.api.config;

import com.eventty.authservice.api.interceptor.UserContextInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class RestTemplateConfig {

    private final CustomRestTemplateErrorHandler customRestTemplateErrorHandler;

    @Bean
    public RestTemplate basicRestTemplate() {
        return new RestTemplate();
    }

    @Bean
    public RestTemplate customRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(customRestTemplateErrorHandler);
        restTemplate.getInterceptors().add(new UserContextInterceptor());
        return restTemplate;
    }
}
