package com.eventty.gateway.util.jwt;

import com.eventty.gateway.api.ApiClient;
import com.eventty.gateway.api.dto.GetNewTokensRequestDTO;
import com.eventty.gateway.api.dto.NewTokensResponseDTO;
import com.eventty.gateway.global.dto.ResponseDTO;
import com.eventty.gateway.global.exception.token.FailGetNewTokensException;
import com.eventty.gateway.global.exception.token.InvalidIssuerException;
import com.eventty.gateway.util.CustomMapper;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.eventty.gateway.util.TokenEnum;
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


    public Map<String, String> getNewTokens(String refreshToken) {
        Claims refreshTokenCliams = getClaimsOrNullOnExpiration(refreshToken);

        String userId = getUserId(refreshTokenCliams);
        GetNewTokensRequestDTO getNewTokensRequestDTO = customMapper.createGetNewTokensRequestDTO(userId, refreshToken);

        ResponseEntity<ResponseDTO<NewTokensResponseDTO>> response = apiClient.getNewTokens(getNewTokensRequestDTO);

        // 새로운 토큰 가져오기 실패
        if (Objects.requireNonNull(response.getBody()).getSuccessResponseDTO().getData() == null)
            throw FailGetNewTokensException.EXCEPTION;

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
                    .requireIssuer(jwtProperties.getIssuer())
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            return null;
        }
        return claims;
    }

    public Claims getClaimsOrThrow(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtProperties.getSecretKey())
                .requireIssuer(jwtProperties.getIssuer())
                .parseClaimsJws(token)
                .getBody();

        return claims;
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
}
