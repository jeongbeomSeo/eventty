package com.eventty.authservice.infrastructure.utils;

import com.eventty.authservice.infrastructure.model.Authority;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AuthenticationConverter {

    private final ObjectMapper objectMapper;

    public Authentication getAuthentication(String userId, String jsonRoels) {
        return new UsernamePasswordAuthenticationToken(userId, "", getAuthorities(jsonRoels));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(String authoritiesJSON) {
        List<Authority> authorityList = getRolesFromJson(authoritiesJSON);
        return authorityList.stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthority()))
                .collect(Collectors.toList());
    }

    private  List<Authority> getRolesFromJson(String jsonRoles) {
        try {
            return objectMapper.readValue(jsonRoles, new TypeReference<List<Authority>>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting JSON to authorities", e);
        }
    }
}
