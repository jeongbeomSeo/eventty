package com.eventty.authservice.infrastructure.utils;

import com.eventty.authservice.applicaiton.service.utils.token.TokenEnum;
import jakarta.servlet.http.Cookie;
import org.springframework.http.ResponseCookie;

public class CookieCreator {

    public static ResponseCookie createAccessTokenCookie(String token) {
        return ResponseCookie.from(TokenEnum.ACCESS_TOKEN.getName(), token)
                .httpOnly(true)
                .path("/")
                //.domain("eventty")
                .maxAge(2 * 60 * 60 + 30 * 60)  // 2시간 30분
                .build();
    }

    public static ResponseCookie createRefreshTokenCookie(String token) {
        return ResponseCookie.from(TokenEnum.REFRESH_TOKEN.getName(), token)
                .httpOnly(true)
                .path("/")
                //.domain("eventty")
                .maxAge(3 * 24 * 60 * 60) // 3일
                .build();
    }

    public static Cookie deleteAccessTokenCookie() {
        // JWT 쿠키 무효화
        Cookie jwtCookie = new Cookie("JWT_TOKEN_NAME", null); // 이름은 실제 JWT 쿠키 이름으로 교체해야 합니다.
        jwtCookie.setPath("/");
        jwtCookie.setHttpOnly(true);
        jwtCookie.setMaxAge(0); // 쿠키의 유효 기간을 0으로 설정하여 즉시 만료시킵니다.

        return jwtCookie;
    }

    public static Cookie deleteRefreshTokenCoolie() {
        // RefreshToken 쿠키 무효화
        Cookie refreshTokenCookie = new Cookie("REFRESH_TOKEN_NAME", null); // 이름은 실제 RefreshToken 쿠키 이름으로 교체해야 합니다.
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setMaxAge(0); // 쿠키의 유효 기간을 0으로 설정하여 즉시 만료시킵니다.

        return refreshTokenCookie;
    }
}
