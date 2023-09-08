package com.eventty.gateway.util.jwt;

import com.eventty.gateway.api.ApiClient;
import com.eventty.gateway.api.dto.GetNewTokensRequestDTO;
import com.eventty.gateway.api.dto.NewTokensResponseDTO;
import com.eventty.gateway.presentation.dto.ResponseDTO;
import com.eventty.gateway.util.CustomMapper;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.ResponseEntity;
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
    private final ApiClient apiClient;
    private final CustomMapper customMapper;


    public Map<String, String> getNewTokens(Map<String, String> tokens) {
        if (tokens.get(TokenEnum.REFRESH_TOKEN.getName()) == null) {} // 예외 발생 => Login Page

        Claims refreshTokenCliams = getClaimsOrThrow(tokens.get(TokenEnum.REFRESH_TOKEN.getName())); // 예외 전부 발생 (내부 로직) => Login Page

        String userId = getUserId(refreshTokenCliams);
        GetNewTokensRequestDTO getNewTokensRequestDTO = customMapper.createGetNewTokensRequestDTO(userId, tokens.get(TokenEnum.REFRESH_TOKEN.getName()));

        ResponseEntity<ResponseDTO<NewTokensResponseDTO>> response = apiClient.getNewTokens(getNewTokensRequestDTO);

        if (Objects.requireNonNull(response.getBody()).getSuccessResponseDTO().getData() == null) {} // 예외 발생 => Login Page

        return Map.of(
                TokenEnum.ACCESS_TOKEN.getName(), response.getBody().getSuccessResponseDTO().getData().getAccessToken(),
                TokenEnum.REFRESH_TOKEN.getName(), response.getBody().getSuccessResponseDTO().getData().getRefreshToken()
        );


    }
    public Claims getClaimsOrNullOnExpiration(String token) {
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

    public Claims getClaimsOrThrow(String token) {
        return Jwts.parser()
                .setSigningKey(jwtProperties.getSecretKey())
                .parseClaimsJws(token)
                .getBody();
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
