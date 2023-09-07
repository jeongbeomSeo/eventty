package com.eventty.gateway.filter;

import com.eventty.gateway.util.CustomMapper;
import com.eventty.gateway.util.jwt.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

@Component
@Slf4j
public class JwtAuthenticationFilter extends AbstractGatewayFilterFactory<JwtAuthenticationFilter.Config> {

    private final String HEADER_USER_ID = "X-User-Id";
    private final String HEADER_AUTHORITIES = "X-User-Authorities";

    private final CustomMapper customMapper;
    private final JwtUtils jwtUtils;

    @Autowired
    public JwtAuthenticationFilter(CustomMapper customMapper, JwtUtils jwtUtils) {
        super(Config.class); // Config.class를 매개변수로 전달
        this.customMapper = customMapper;
        this.jwtUtils = jwtUtils;
    }

    public static class Config {
        // 만약 필터에 필요한 추가 설정이 있다면 이곳에 속성들을 추가
        // 예: private String someProperty;
        // public String getSomeProperty() { return someProperty; }
        // public void setSomeProperty(String someProperty) { this.someProperty = someProperty; }
    }

    @Override
    public GatewayFilter apply(Config config) {

        // 함수형 인터페이스의 인스턴스를 간결하게 표한하는 람다 표현식
        return ((exchange, chain) -> {
            MultiValueMap<String, HttpCookie> cookies = exchange.getRequest().getCookies();

            String jwtToken = customMapper.jwtMapping(cookies);

            Claims claims = jwtUtils.getClaims(jwtToken);

            ServerHttpRequest requestWithHeader = exchange.getRequest().
                    mutate()
                    .header(HEADER_USER_ID, jwtUtils.getUserId(claims))
                    .header(HEADER_AUTHORITIES, jwtUtils.getAuthoritiesToJson(claims))
                    .build();

            // 확인하기 위한 로깅
            log.warn("{}이(가) 지나갑니다 !! {} 여기로 갑니다!!", claims.getSubject(),exchange.getRequest().getPath());

            return chain.filter(exchange.mutate().request(requestWithHeader).build());


        /* 람다 표현식을 사용하지 않았을 때
        return new GatewayFilter() {
            @Override
            public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
                // ... 내부 로직 ...
            }
        };*/
        });
    }
}
