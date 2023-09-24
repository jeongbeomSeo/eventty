package com.eventty.authservice.domain.Enum;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;

import java.net.URI;

@Getter
@RequiredArgsConstructor
public enum OAuth {

    GOOGLE("Google",
            URI.create("https://www.googleapis.com/oauth2/v2/userinfo"),
            HttpMethod.GET),
    NAVER("naver",
            URI.create("https://openapi.naver.com/v1/nid/me"),
            HttpMethod.GET),
    KAKAO("kakao",
            URI.create("https://kapi.kakao.com/v2/user/me"),
            HttpMethod.GET);

    private final String socialName;
    private final URI uri;
    private final HttpMethod method;
}
