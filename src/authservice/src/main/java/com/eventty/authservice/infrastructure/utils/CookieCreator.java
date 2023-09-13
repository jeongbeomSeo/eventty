package com.eventty.authservice.infrastructure.utils;

import com.eventty.authservice.applicaiton.service.utils.token.TokenEnum;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseCookie;

public class CookieCreator {

    public static ResponseCookie createAccessTokenCookie(String token) {
        return ResponseCookie.from(TokenEnum.ACCESS_TOKEN.getName(), token)
                .path("/")
                .httpOnly(true)
                .maxAge(2 * 60 * 60 + 30 * 60)
                .build();
    }

    public static ResponseCookie createRefreshTokenCookie(String token) {
        return ResponseCookie.from(TokenEnum.REFRESH_TOKEN.getName(), token)
                .path("/")
                .httpOnly(true)
                .maxAge(3 * 24 * 60 * 60)
                .build();
    }

    // JWT 쿠키 무효화
    public static ResponseCookie deleteAccessTokenCookie() {
        return ResponseCookie.from(TokenEnum.ACCESS_TOKEN.getName())
                .httpOnly(true)
                .maxAge(0) // 쿠키의 유효 기간을 0으로 설정하여 즉시 만료시킵니다.
                .build();
    }

    // RefreshToken 쿠키 무효화
    public static ResponseCookie deleteRefreshTokenCookie() {
        return ResponseCookie.from(TokenEnum.REFRESH_TOKEN.getName())
                .path("/")
                .httpOnly(true)
                .maxAge(0)
                .build();
    }
}
