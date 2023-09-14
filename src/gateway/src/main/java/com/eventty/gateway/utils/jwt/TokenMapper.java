package com.eventty.gateway.utils.jwt;

import com.eventty.gateway.dto.TokenDetails;
import org.springframework.http.HttpCookie;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import java.util.List;

@Component
public class TokenMapper {

    public TokenDetails CookiesToTokenDetails(MultiValueMap<String, HttpCookie> cookies) {
        String jwtToken = jwtMapping(cookies);
        String refreshToken = refreshTokenMapping(cookies);

        return TokenDetails.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .needsUpdate(false)
                .claims(null)
                .build();
    }

    // 맨 처음은 필터에서 AccessToken의 유무를 검증하기 때문에 없는 경우의 발생은 새로 받아오는 경우에 발생
    public String jwtMapping(MultiValueMap<String, HttpCookie> cookies) {
        List<HttpCookie> cookie = cookies.get(TokenEnum.ACCESS_TOKEN.getName());

        if (cookie == null) return null;

        return cookie.get(0).toString().replace(TokenEnum.ACCESS_TOKEN.getName()+ "=", "");
    }

    public String refreshTokenMapping(MultiValueMap<String, HttpCookie> cookies) {
        List<HttpCookie> cookie = cookies.get(TokenEnum.REFRESH_TOKEN.getName());

        if (cookie == null) return null;

        return cookie.get(0).toString().replace(TokenEnum.REFRESH_TOKEN.getName()+ "=", "");
    }
}
