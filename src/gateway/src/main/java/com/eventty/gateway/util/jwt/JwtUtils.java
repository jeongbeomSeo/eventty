package com.eventty.gateway.util.jwt;

import org.springframework.stereotype.Component;

import com.eventty.gateway.presentation.TokenEnum;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import lombok.RequiredArgsConstructor;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Component
@RequiredArgsConstructor
public class JwtUtils {

    private final JwtProperties jwtProperties;
    private final ObjectMapper objectMapper;

    public Claims getClaims(String token) {

        // SigningKey가 적절하지 않은 경우 Illegal Exception 발생
        Claims claims = Jwts.parser()
                .setSigningKey(jwtProperties.getSecretKey())
                .parseClaimsJws(token)
                .getBody();
        validationCheck(claims);

        return claims;
    }

    public String getUserId(Claims claims) {
        return claims.get(TokenEnum.USERID.getName())
                .toString();
    }

    public String getAuthoritiesToJson(Claims claims) {
        List<String> roles = getRolesFromClaims(claims);
        try {
            return objectMapper.writeValueAsString(roles);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting authorities to JSON", e);
        }
    }

    private List<String> getRolesFromClaims(Claims claims) {
        return objectMapper.convertValue(
                claims.get(TokenEnum.AUTHORIZATION.toString()),
                new TypeReference<List<String>>() {});
    }
    private void validationCheck(Claims claims) {
        if (!claims.getIssuer().equals(jwtProperties.getIssure())) {
            throw new RuntimeException("Invalid issuer");
        }

        if (claims.getExpiration().before(new Date())) {
            throw new RuntimeException("Token is expired");
        }
    }

    /* Authentication 객체는 직렬화해서 보낼 수 있지만, 데이터의 크기와 복잡성 때문에 각 서비스에서 만드는 것이 효율적
    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);

        // 형태: userId, "", ROLE_USER
        return new UsernamePasswordAuthenticationToken(
                claims.get(TokenEnum.USERID.toString()), "", getAuthorities(claims));
    }
    */


    /*  권한 List로 만드는 작업은 각 서비스에서 할 에정
    private Collection<? extends GrantedAuthority> getAuthorities(Claims claims) {
        List<String> roles = getRolesFromClaims(claims);
        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
    */

}
