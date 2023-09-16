package com.eventty.gateway.utils;

import com.eventty.gateway.api.dto.AuthenticateUserRequestDTO;
import com.eventty.gateway.api.dto.GetNewTokensRequestDTO;
import com.eventty.gateway.utils.jwt.TokenEnum;
import org.springframework.http.HttpCookie;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import java.util.List;

@Component
public class CustomMappper {
    public GetNewTokensRequestDTO createGetNewTokensRequestDTO(String userId, String refreshToken) {
        return GetNewTokensRequestDTO.builder()
                .userId(Long.parseLong(userId))
                .refreshToken(refreshToken)
                .build();
    }

    public AuthenticateUserRequestDTO authenticateUserRequestDTO(MultiValueMap<String, HttpCookie> cookies, String csrfToken) {

    }

    private String[] convertCookiesToTokens(MultiValueMap<String, HttpCookie> cookies) {

    }

    // JWT와 CSRF 토큰이 없는 경우는 예외 발생
    private String jwtMapping(MultiValueMap<String, HttpCookie> cookies) {
        List<HttpCookie> cookie = cookies.get(TokenEnum.ACCESS_TOKEN.getName());

        if (cookie == null) return null;

        return cookie.get(0).toString().replace(TokenEnum.ACCESS_TOKEN.getName()+ "=", "");
    }

    private String refreshTokenMapping(MultiValueMap<String, HttpCookie> cookies) {
        List<HttpCookie> cookie = cookies.get(TokenEnum.REFRESH_TOKEN.getName());

        if (cookie == null) return null;

        return cookie.get(0).toString().replace(TokenEnum.REFRESH_TOKEN.getName()+ "=", "");
    }
}
