package com.eventty.gateway.util;

import com.eventty.gateway.api.dto.GetNewTokensRequestDTO;
import com.eventty.gateway.global.exception.token.AccessTokenIsEmptyException;
import com.eventty.gateway.global.exception.token.RefreshTokenIsExpiredException;
import org.springframework.http.HttpCookie;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import java.util.List;

@Component
public class CustomMapper {

    public GetNewTokensRequestDTO createGetNewTokensRequestDTO(String userId, String refreshToken) {
        return GetNewTokensRequestDTO.builder()
                .userId(Long.parseLong(userId))
                .refreshToken(refreshToken)
                .build();
    }

    public String jwtMapping(MultiValueMap<String, HttpCookie> cookies) {
        List<HttpCookie> cookie = cookies.get(TokenEnum.ACCESS_TOKEN.getName());

        if (cookie == null)
            throw AccessTokenIsEmptyException.EXCEPTION;

        return cookie.get(0).toString().replace(TokenEnum.ACCESS_TOKEN.getName()+ "=", "");
    }

    public String refreshTokenMapping(MultiValueMap<String, HttpCookie> cookies) {
        List<HttpCookie> cookie = cookies.get(TokenEnum.REFRESH_TOKEN.getName());

        if (cookie == null)
            throw RefreshTokenIsExpiredException.EXCEPTION;

        return cookie.get(0).toString().replace(TokenEnum.REFRESH_TOKEN.getName()+ "=", "");
    }
}
