package com.eventty.gateway.util;

import com.eventty.gateway.presentation.TokenEnum;
import org.springframework.http.HttpCookie;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import java.util.List;

@Component
public class CustomMapper {

    public String jwtMapping(MultiValueMap<String, HttpCookie> cookies) {
        List<HttpCookie> cookie = cookies.get(TokenEnum.ACCESS_TOKEN.getName());
        if (cookie != null)  {
            // "accessToken=~~~" 과 같은 형태로 담겨오기 때문에 replace함수 사용
            return cookie.get(0).toString().replace(TokenEnum.ACCESS_TOKEN.getName()+ "=", "");
        }
        // 예외 발생
        return null;
    }

    public String refreshTokenMapping(MultiValueMap<String, HttpCookie> cookies) {
        List<HttpCookie> cookie = cookies.get(TokenEnum.REFRESH_TOKEN.getName());
        if (cookie != null) {
            return cookie.get(0).toString().replace(TokenEnum.REFRESH_TOKEN.getName()+ "=", "");
        }
        // 예외 발생
        return null;
    }
}
