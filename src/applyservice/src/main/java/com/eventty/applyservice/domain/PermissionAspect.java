package com.eventty.applyservice.domain;

import com.eventty.applyservice.domain.annotation.Permission;
import com.eventty.applyservice.domain.code.UserRole;
import com.eventty.applyservice.domain.exception.PermissionDeniedException;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@Aspect
@Component
public class PermissionAspect {

    // Controller Method가 실행되기 '직전에만' 실행
    @Before("@annotation(permission)")
    public void checkAuthorization(JoinPoint joinPoint, Permission permission) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 에러 헨들링
        if (authentication == null || !hasAnyPermission(authentication, permission.Roles()))
            throw new PermissionDeniedException();

        log.info("Successfully verified permission for user {}.", authentication.getPrincipal());
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