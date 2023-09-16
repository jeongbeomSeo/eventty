package com.eventty.gateway.utils.jwt;

import com.eventty.gateway.api.dto.AuthenticateUserRequestDTO;
import com.eventty.gateway.api.dto.GetNewTokensRequestDTO;
import com.eventty.gateway.dto.TokenDetails;
import com.eventty.gateway.global.exception.token.NoAccessTokenException;
import com.eventty.gateway.global.exception.token.NoCsrfTokenException;
import org.springframework.http.HttpCookie;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Map;

@Component
public class TokenMapper {
    private final String HEADER_CSRF = "X-CSRF-Token";


    public TokenDetails CookiesToTokenDetails(MultiValueMap<String, HttpCookie> cookies) {
        String jwtToken = getJwt(cookies);
        String refreshToken = getRefreshToken(cookies);

        return TokenDetails.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .needsUpdate(false)
                .claims(null)
                .build();
    }

    public AuthenticateUserRequestDTO getNewTokensRequestDTO(MultiValueMap<String, HttpCookie> cookies, Map<String, List<String>> headers) {
        return new AuthenticateUserRequestDTO(
                getJwt(cookies),
                getRefreshToken(cookies),
                getCsrfToken(headers)
        );
    }

    // 맨 처음은 필터에서 AccessToken의 유무를 검증하기 때문에 없는 경우의 발생은 새로 받아오는 경우에 발생
    private String getJwt(MultiValueMap<String, HttpCookie> cookies) {
        List<HttpCookie> cookie = cookies.get(TokenEnum.ACCESS_TOKEN.getName());

        if (cookie == null)
            throw new NoAccessTokenException();

        return cookie.get(0).toString().replace(TokenEnum.ACCESS_TOKEN.getName()+ "=", "");
    }

    private String getRefreshToken(MultiValueMap<String, HttpCookie> cookies) {
        List<HttpCookie> cookie = cookies.get(TokenEnum.REFRESH_TOKEN.getName());

        if (cookie == null) return null;

        return cookie.get(0).toString().replace(TokenEnum.REFRESH_TOKEN.getName()+ "=", "");
    }
    private String getCsrfToken(Map<String, List<String>> headers) {
        if (headers == null)
            throw new NoCsrfTokenException();

        for (String headerName: headers.keySet()) {
            if (headerName != null && headerName.equalsIgnoreCase(HEADER_CSRF) && !headers.get(headerName).isEmpty()) {
                return headers.get(headerName).get(0);
            }
        }
        throw new NoCsrfTokenException();
    }
}
