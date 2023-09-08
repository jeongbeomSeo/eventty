package com.eventty.gateway.util.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.stereotype.Component;

import com.eventty.gateway.presentation.TokenEnum;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import lombok.RequiredArgsConstructor;

import java.util.*;

@Component
@RequiredArgsConstructor
public class JwtUtils {

    private final JwtProperties jwtProperties;
    private final ObjectMapper objectMapper;

    public Claims getClaims(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(jwtProperties.getSecretKey())
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            return null;
        }

        return claims;
    }

    public boolean isNotExpiredToken(Claims claims) {
        return claims.getExpiration().before(new Date());
    }

    public String getUserId(Claims claims) {
        return claims.get(TokenEnum.USERID.getName())
                .toString();
    }

    public String getAuthoritiesToJson(Claims claims) {
        List<Authority> roles = getRolesFromClaims(claims);
        try {
            return objectMapper.writeValueAsString(roles);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting authorities to JSON", e);
        }
    }

    // 안에 담긴 형태가 authorities안에 [{authority=ROLE_USER}] 형태로 담겨 오는 상황
    private List<Authority> getRolesFromClaims(Claims claims) {
        return objectMapper.convertValue(
                claims.get(TokenEnum.AUTHORIZATION.getName()),
                new TypeReference<List<Authority>>() {}
        );
    }
    private void issuerCheck(Claims claims) {
        if (!claims.getIssuer().equals(jwtProperties.getIssure())) {
            throw new RuntimeException("Invalid issuer");
        }
    }
}
