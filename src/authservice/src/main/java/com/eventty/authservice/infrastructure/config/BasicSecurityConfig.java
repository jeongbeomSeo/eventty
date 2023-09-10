package com.eventty.authservice.infrastructure.config;

import com.eventty.authservice.applicaiton.service.utils.token.TokenEnum;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
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
                            .requestMatchers(PathRequest.toH2Console()) // H2가 나의 로컬 DB 접근할 수 있도록 허용
                            .permitAll()
                            .anyRequest()
                            .permitAll();

                })
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(httpSecurityLogoutConfigurer -> {
                    httpSecurityLogoutConfigurer
                            .logoutUrl("/logout")
                            .invalidateHttpSession(true)
                            .clearAuthentication(true)
                            .deleteCookies(TokenEnum.ACCESS_TOKEN.getName(), TokenEnum.REFRESH_TOKEN.getName())
                            .logoutSuccessHandler((request, response, authentication) ->
                                    response.setStatus(HttpServletResponse.SC_OK));
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
