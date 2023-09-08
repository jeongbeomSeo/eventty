package com.eventty.gateway.filter;

import com.eventty.gateway.api.ApiClient;
import com.eventty.gateway.api.dto.GetNewTokensRequestDTO;
import com.eventty.gateway.api.dto.NewTokensResponseDTO;
import com.eventty.gateway.presentation.TokenEnum;
import com.eventty.gateway.presentation.dto.ResponseDTO;
import com.eventty.gateway.util.CookieCreator;
import com.eventty.gateway.util.CustomMapper;
import com.eventty.gateway.util.jwt.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpCookie;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
@Slf4j
public class JwtAuthenticationFilter extends AbstractGatewayFilterFactory<JwtAuthenticationFilter.Config> {

    private final String HEADER_USER_ID = "X-User-Id";
    private final String HEADER_AUTHORITIES = "X-User-Authorities";

    private final CustomMapper customMapper;
    private final JwtUtils jwtUtils;

    @Autowired
    public JwtAuthenticationFilter(CustomMapper customMapper, JwtUtils jwtUtils, CookieCreator cookieCreator) {
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
            Map<String, String> tokens = customMapper.tokenMapping(cookies);

            if (tokens.get(TokenEnum.ACCESS_TOKEN.getName()) == null) {} // 예외 발생

            // 이 로직 수정 필요 (내부에서 try-catch로 ExpiredJwtException만 잡아서 null값을 return해주는 형태로 진행중
            Claims jwtClaims = jwtUtils.getClaimsOrNullOnExpiration(tokens.get(TokenEnum.ACCESS_TOKEN.getName()));
            boolean isExpired = (jwtClaims == null);

            if (isExpired) {
                // 새로 받아온 Tokens 저장
                tokens = jwtUtils.getNewTokens(tokens);

                log.warn("토큰 받아옵니다.");

                // 가져온 토큰을 통해서 Cliams 가져오기. 만약, 여기서도 예외 발생한다면 그냥 전부 발생시키기
                jwtClaims = jwtUtils.getClaimsOrThrow(tokens.get(TokenEnum.ACCESS_TOKEN.getName()));
            }

            // Authentication 객체를 직렬화해서 보낼 수 있지만, 데이터의 크기와 복잡성 때문에 각 서비스에서 만드는 것이 효율적
            ServerHttpRequest requestWithHeader = exchange.getRequest().
                    mutate()
                    .header(HEADER_USER_ID, jwtUtils.getUserId(jwtClaims))
                    .header(HEADER_AUTHORITIES, jwtUtils.getAuthoritiesToJson(jwtClaims))
                    .build();

            // 확인하기 위한 로깅
            log.warn("{}이(가) 지나갑니다 !! {} 여기로 갑니다!!", jwtClaims.getSubject(),exchange.getRequest().getPath());

            if (!isExpired)
                return chain.filter(exchange.mutate().request(requestWithHeader).build());
            else {
                    // Response에 새로 받아온 JWT와 Refresh Token Update
                    final Map<String, String> finalTokens = tokens;
                    return chain.filter(exchange.mutate().request(requestWithHeader).build())
                        .then(Mono.fromRunnable(() -> {
                            ServerHttpResponse serverHttpResponse = exchange.getResponse();
                            // 위에서 받아온 토큰 Response 쿠키 설정

                            log.warn("token update");

                            ResponseCookie jwtCookie = CookieCreator.createAccessTokenCookie(finalTokens.get(TokenEnum.ACCESS_TOKEN.getName()));
                            ResponseCookie refreshTokenCookie = CookieCreator.createRefreshTokenCookie(finalTokens.get(TokenEnum.REFRESH_TOKEN.getName()));

                            serverHttpResponse.addCookie(jwtCookie);
                            serverHttpResponse.addCookie(refreshTokenCookie);
                        }));
            }

        });
    }
}


/* 람다 표현식을 사용하지 않았을 때
return new GatewayFilter() {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // ... 내부 로직 ...
    }
};*/
