package com.eventty.gateway.utils.jwt;

import com.eventty.gateway.api.dto.NewTokensResponseDTO;
import com.eventty.gateway.dto.TokenDetails;
import com.eventty.gateway.global.dto.ResponseDTO;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import lombok.RequiredArgsConstructor;
import org.springframework.util.MultiValueMap;

import java.util.*;

@Component
@RequiredArgsConstructor
public class JwtUtils {

    private final JwtProperties jwtProperties;
    private final ObjectMapper objectMapper;
    private final TokenMapper tokenMapper;

    public TokenDetails initTokenDetails(MultiValueMap<String, HttpCookie> cookies) {
        return tokenMapper.CookiesToTokenDetails(cookies);
    }

    public Claims getClaimsOrNullOnExpiration(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(jwtProperties.getSecretKey())
                    .requireIssuer(jwtProperties.getIssuer())
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            return null;
        }
        return claims;
    }

    public TokenDetails updateNewTokenInfo(ResponseEntity<ResponseDTO<NewTokensResponseDTO>> response) {
        return TokenDetails.builder()
                .accessToken(response.getBody().getSuccessResponseDTO().getData().getAccessToken())
                .refreshToken(response.getBody().getSuccessResponseDTO().getData().getRefreshToken())
                .needsUpdate(true)
                .claims(getClaimsOrThrow(response.getBody().getSuccessResponseDTO().getData().getAccessToken()))
                .build();
    }

    public String getUserId(String token) {
        Claims claims = getClaimsOrNullOnExpiration(token);

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

    private Claims getClaimsOrThrow(String token) {
        return Jwts.parser()
                .setSigningKey(jwtProperties.getSecretKey())
                .requireIssuer(jwtProperties.getIssuer())
                .parseClaimsJws(token)
                .getBody();
    }

    // 안에 담긴 형태가 authorities안에 [{authority=ROLE_USER}] 형태로 담겨 오는 상황
    private List<Authority> getRolesFromClaims(Claims claims) {
        return objectMapper.convertValue(
                claims.get(TokenEnum.AUTHORIZATION.getName()),
                new TypeReference<List<Authority>>() {}
        );
    }
}
