package com.eventty.authservice.infrastructure.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

    private final String HEADER_USER_ID = "X-User-Id";
    private final String HEADER_AUTHORITIES = "X-User-Authorities";
    private final String USER_ID = "userId";
    private final String AUTHORITIES = "authorities";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        log.info("Current Position: Authentication interceptor");

        // 헤더에서 user Id 와 권한 정보 추출
        String userId = request.getHeader(HEADER_USER_ID);
        String authorities = request.getHeader(HEADER_AUTHORITIES);

        // Authentication가 필요한 데이터가 담겨오지 않았다면 Token이 필요하지 않은 요청이라고 판단하고 넘기기
        if (userId == null || userId.isEmpty()) return true;

        // 임시로 HttpServletRequest의 속성에 저장
        request.setAttribute(USER_ID, userId);
        request.setAttribute(AUTHORITIES, authorities);

        log.info("User Id: {}\n", userId);

        return true;
    }


}
