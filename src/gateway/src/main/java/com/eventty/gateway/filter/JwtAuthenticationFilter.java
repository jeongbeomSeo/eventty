package com.eventty.gateway.filter;

import com.eventty.gateway.api.ApiClient;
import com.eventty.gateway.api.dto.GetNewTokensRequestDTO;
import com.eventty.gateway.api.dto.NewTokensResponseDTO;
import com.eventty.gateway.presentation.TokenEnum;
import com.eventty.gateway.presentation.dto.ResponseDTO;
import com.eventty.gateway.presentation.dto.SuccessResponseDTO;
import com.eventty.gateway.util.CustomMapper;
import com.eventty.gateway.util.jwt.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
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
    private final ApiClient apiClient;

    @Autowired
    public JwtAuthenticationFilter(CustomMapper customMapper, JwtUtils jwtUtils, ApiClient apiClient) {
        super(Config.class); // Config.class를 매개변수로 전달
        this.customMapper = customMapper;
        this.jwtUtils = jwtUtils;
        this.apiClient = apiClient;
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

            Claims jwtClaims = jwtUtils.getClaims(tokens.get(TokenEnum.ACCESS_TOKEN.getName()));

            // 이 로직 수정 필요 (내부에서 try-catch로 ExpiredJwtException만 잡아서 null값을 return해주는 형태로 진행중
            boolean isExpired = (jwtClaims == null);

            if (isExpired) {

                if (tokens.get(TokenEnum.REFRESH_TOKEN.getName()) == null) {} // 예외 발생 => Login Page로 Redirect

                Claims refreshTokenClaims = jwtUtils.getClaims(tokens.get(TokenEnum.REFRESH_TOKEN.getName()));

                if (refreshTokenClaims == null) {} // RefreshToken의 만료 기간도 지난 경우 그냥 예외 터트리기

                String userId = jwtUtils.getUserId(refreshTokenClaims);

                // Auth Server로 요청 보내서 새로운 JWT와 RefreshToken 받아오기
                // 이 작업이 비동기적으로 이루어진다면 Mono나 Flux를 반환하는 메서드로 처리하고,
                // .block()과 같은 메서드를 사용하여 결과를 기다려야 합니다.

                GetNewTokensRequestDTO getNewTokensRequestDTO = customMapper.createGetNewTokensRequestDTO(userId, tokens.get(TokenEnum.REFRESH_TOKEN.getName()));

                ResponseEntity<ResponseDTO<NewTokensResponseDTO>> response = apiClient.getNewTokens(getNewTokensRequestDTO);

                System.out.println(response);
                // 실패할 경우 예외 발생
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
                return chain.filter(exchange.mutate().request(requestWithHeader).build())
                        .then(Mono.fromRunnable(() -> {
                            // 위에서 받아온 토큰 Response 쿠키 설정


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
