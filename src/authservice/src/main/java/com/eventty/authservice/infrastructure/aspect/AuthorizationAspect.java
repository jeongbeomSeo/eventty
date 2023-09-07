package com.eventty.authservice.infrastructure.aspect;

import com.eventty.authservice.domain.Enum.UserRole;
import com.eventty.authservice.domain.exception.PermissionDeniedException;
import com.eventty.authservice.infrastructure.annotation.Auth;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Aspect
@Component
public class AuthorizationAspect {

    // Controller Method가 실행되기 '직전에만' 실행
    @Before("@annotation(auth)")
    public void checkAuthorization(JoinPoint joinPoint, Auth auth){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 에러 헨들링
        if (authentication == null || !hasAnyPermission(authentication, auth.Roles()))
            throw PermissionDeniedException.EXCEPTION;
    }

    private boolean hasAnyPermission(Authentication authentication, UserRole[] roles) {
        Set<String> requiredAuthorities = Arrays.stream(roles)
                .map(UserRole::getRole)
                .collect(Collectors.toSet());

        Set<String> userAuthorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        // userAuthorities와 requiredAuthorities의 교집합이 있으면 true 반환
        return userAuthorities.stream().anyMatch(requiredAuthorities::contains);
    }
}





