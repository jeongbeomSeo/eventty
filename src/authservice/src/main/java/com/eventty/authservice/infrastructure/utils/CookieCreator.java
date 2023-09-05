package com.eventty.authservice.infrastructure.utils;

import org.springframework.http.ResponseCookie;

public class CookieCreator {

    public static ResponseCookie createAccessTokenCookie(String token) {
        return ResponseCookie.from("accessToken", token)
                .httpOnly(true)
                .path("/")
                .domain("eventty")
                .maxAge(2 * 60 * 60 + 30 * 60)  // 2시간 30분
                .build();
    }

    public static ResponseCookie createRefreshTokenCookie(String token) {
        return ResponseCookie.from("RefreshToken", token)
                .httpOnly(true)
                .path("/")
                .domain("eventty")
                .maxAge(3 * 24 * 60 * 60) // 3일
                .build();
    }


}
