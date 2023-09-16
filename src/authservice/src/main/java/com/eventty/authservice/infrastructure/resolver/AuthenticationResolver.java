package com.eventty.authservice.infrastructure.resolver;

import com.eventty.authservice.infrastructure.utils.AuthenticationConverter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;


@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticationResolver implements HandlerMethodArgumentResolver {

    private final String USER_ID = "userId";
    private final String AUTHORITIES = "authorities";
    private final AuthenticationConverter authenticationConverter;


    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return LoginUser.class
                .isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        log.info("Current Position: Authentication Resolver");

        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();

        // RequestAttributes에서 정보 가져오기
        String userId = (String) request.getAttribute(USER_ID);
        String authoritiesJSON = (String) request.getAttribute(AUTHORITIES);

        Authentication authentication = authenticationConverter.getAuthentication(userId, authoritiesJSON);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        log.info("Successfully saved {}'s authentication in the security context holder.\n", userId);

        return authentication.getPrincipal();
    }
}
