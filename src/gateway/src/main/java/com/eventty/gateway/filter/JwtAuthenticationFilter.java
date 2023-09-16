package com.eventty.gateway.filter;

import com.eventty.gateway.api.ApiClient;
import com.eventty.gateway.api.dto.AuthenticationDetailsResponseDTO;
import com.eventty.gateway.dto.TokenDetails;
import com.eventty.gateway.global.dto.ResponseDTO;
import com.eventty.gateway.global.dto.SuccessResponseDTO;
import com.eventty.gateway.global.exception.token.NoAccessTokenException;
import com.eventty.gateway.service.TokenAuthenticationService;
import com.eventty.gateway.service.TokenAuthenticationServiceImpl;
import com.eventty.gateway.utils.CustomMappper;
import com.eventty.gateway.utils.jwt.TokenEnum;
import com.eventty.gateway.utils.CookieCreator;
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

import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class JwtAuthenticationFilter extends AbstractGatewayFilterFactory<JwtAuthenticationFilter.Config> {

    private final String HEADER_USER_ID = "X-User-Id";
    private final String HEADER_AUTHORITIES = "X-User-Authorities";
    private final String HEADER_CSRF = "X-CSRF-Token";

    // 의존성 제거
    private final TokenAuthenticationService tokenAuthenticationService;
    private final ApiClient apiClient;
    private final CustomMappper customMappper;

    @Autowired
    public JwtAuthenticationFilter(TokenAuthenticationServiceImpl tokenAuthenticationServiceImpl, ApiClient apiClient, CustomMappper customMappper) {
        super(Config.class); // Config.class를 매개변수로 전달
        this.tokenAuthenticationService = tokenAuthenticationServiceImpl;
        this.apiClient = apiClient;
        this.customMappper = customMappper;
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
            Map<String, List<String>> headers = exchange.getRequest().getHeaders();
            boolean hasAccessToken = cookies.get(TokenEnum.ACCESS_TOKEN.getName()) != null;
            if (!hasAccessToken)
                throw new NoAccessTokenException();

            // 이 부분이 API 요청 보내는 로직으로 변경되어야 함
            ResponseEntity<ResponseDTO<AuthenticationDetailsResponseDTO>> response = apiClient.authenticateUser(customMappper.authenticateUserRequestDTO(cookies, headers));

            TokenDetails tokenDetails = tokenAuthenticationService.getTokenDetails(cookies);

            // Authentication 객체를 직렬화해서 보낼 수 있지만, 데이터의 크기와 복잡성 때문에 각 서비스에서 만드는 것이 효율적
            ServerHttpRequest requestWithHeader = exchange.getRequest().
                    mutate()
                    .header(HEADER_USER_ID, tokenDetails.getUserId())
                    .header(HEADER_AUTHORITIES, tokenDetails.getAuthoritiesJson())
                    .build();

            log.debug("User: {}, Path: {}", tokenDetails.getClaims().getSubject(), exchange.getRequest().getPath());

            if (!tokenDetails.isNeedsUpdate())
                return chain.filter(exchange.mutate().request(requestWithHeader).build());
            else {
                    // Response에 새로 받아온 JWT와 Refresh Token Update
                    return chain.filter(exchange.mutate().request(requestWithHeader).build())
                        .then(Mono.fromRunnable(() -> {
                            log.debug("PostLogger: Authentication Filter get new tokens and update");
                            ServerHttpResponse serverHttpResponse = exchange.getResponse();

                            // 위에서 받아온 토큰 Response 쿠키 설정
                            log.debug("Tokens update!!");
                            ResponseCookie jwtCookie = CookieCreator.createAccessTokenCookie(tokenDetails.getAccessToken());
                            ResponseCookie refreshTokenCookie = CookieCreator.createRefreshTokenCookie(tokenDetails.getRefreshToken());

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
