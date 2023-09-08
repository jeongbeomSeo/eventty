package com.eventty.gateway.util;

import com.eventty.gateway.api.dto.GetNewTokensRequestDTO;
import com.eventty.gateway.presentation.TokenEnum;
import org.springframework.http.HttpCookie;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CustomMapper {

    public GetNewTokensRequestDTO createGetNewTokensRequestDTO(String userId, String refreshToken) {
        return GetNewTokensRequestDTO.builder()
                .userId(Long.parseLong(userId))
                .refreshToken(refreshToken)
                .build();
    }

    public Map<String, String> tokenMapping(MultiValueMap<String, HttpCookie> cookies) {
        Map<String, String> result = new HashMap<>();

        result.put(TokenEnum.ACCESS_TOKEN.getName(), jwtMapping(cookies));
        result.put(TokenEnum.REFRESH_TOKEN.getName(), refreshTokenMapping(cookies));

        return result;
    }

    private String jwtMapping(MultiValueMap<String, HttpCookie> cookies) {
        List<HttpCookie> cookie = cookies.get(TokenEnum.ACCESS_TOKEN.getName());
        if (cookie != null)  {
            // "accessToken=~~~" 과 같은 형태로 담겨오기 때문에 replace함수 사용
            return cookie.get(0).toString().replace(TokenEnum.ACCESS_TOKEN.getName()+ "=", "");
        }
        return null;
    }

    private String refreshTokenMapping(MultiValueMap<String, HttpCookie> cookies) {
        List<HttpCookie> cookie = cookies.get(TokenEnum.REFRESH_TOKEN.getName());
        if (cookie != null) {
            return cookie.get(0).toString().replace(TokenEnum.REFRESH_TOKEN.getName()+ "=", "");
        }
        return null;
    }
}
