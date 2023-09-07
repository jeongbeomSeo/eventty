package com.eventty.authservice.infrastructure.resolver;

import com.eventty.authservice.infrastructure.utils.AuthenticationConverter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;


@Component
@RequiredArgsConstructor
public class AuthenticationResolver implements HandlerMethodArgumentResolver {

    private final String USER_ID = "userId";
    private final String AUTHORITIES = "authorities";
    private final AuthenticationConverter authenticationConverter;


    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return Authentication.class
                .isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();

        // RequestAttributes에서 정보 가져오기
        String userId = (String) request.getAttribute(USER_ID);
        String authoritiesJSON = (String) request.getAttribute(AUTHORITIES);

        // 필요한 정보 없다면 넘기기
        if (userId == null) {
            return null;
        }

        Authentication authentication = authenticationConverter.getAuthentication(userId, authoritiesJSON);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return authentication;
    }
}
