package com.eventty.gateway.service;

import com.eventty.gateway.api.ApiClient;
import com.eventty.gateway.api.dto.GetNewTokensRequestDTO;
import com.eventty.gateway.api.dto.NewTokensResponseDTO;
import com.eventty.gateway.dto.TokenDetails;
import com.eventty.gateway.global.dto.ResponseDTO;
import com.eventty.gateway.global.exception.token.FailGetNewTokensException;
import com.eventty.gateway.global.exception.token.RefreshTokenIsEmptyException;
import com.eventty.gateway.utils.CustomMappper;
import com.eventty.gateway.utils.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenAuthenticationServiceImpl implements TokenAuthenticationService {

    private final ApiClient apiClient;
    private final JwtUtils jwtUtils;
    private final CustomMappper customMappper;

    // Token 관련 정보가 담긴 TokenDetails 객체 받아오기
    public TokenDetails getTokenDetails(MultiValueMap<String, HttpCookie> cookies) {
        // cookies에 담겨온 JWT 와 Refresh Token를 이용해서 TokenDetails 초기화
        TokenDetails tokenDetails = jwtUtils.initTokenDetails(cookies);

        // 담겨온 JWT를 파싱하여 Claims 저장
        tokenDetails.setClaims(jwtUtils.getClaimsOrNullOnExpiration(tokenDetails.getAccessToken()));

        // JWT 만료된 경우
        if (tokenDetails.getClaims() == null) {
            log.debug("JWT is Expired!!");

            // JWT가 만료되었고 Refresh Token도 없는 경우 예외 발생
            if (tokenDetails.getRefreshToken() == null)
                throw new RefreshTokenIsEmptyException();

            String userId = jwtUtils.getUserId(tokenDetails.getRefreshToken());
            GetNewTokensRequestDTO getNewTokensRequestDTO = customMappper.createGetNewTokensRequestDTO(userId, tokenDetails.getRefreshToken());

            // 새로운 토큰 받아오기
            ResponseEntity<ResponseDTO<NewTokensResponseDTO>> response = apiClient.getNewTokens(getNewTokensRequestDTO);

            // 새로운 토큰을 받아오지 못한 경우 => 예외 발생
            if (!Objects.requireNonNull(response.getBody()).isSuccess())
                throw new FailGetNewTokensException(userId);

            log.debug("New tokens have been received from the authentication service server!!");

            // tokenDetails 전부 업데이트 한 후 filter에 건네주기
            tokenDetails = jwtUtils.createNewTokenDetails(response);

            tokenDetails.setUserId(userId);
            tokenDetails.setAuthoritiesJson(jwtUtils.getAuthoritiesToJson(tokenDetails.getClaims()));
        }
        else {  // JWT가 유효한 경우
            String userId = jwtUtils.getUserId(tokenDetails.getAccessToken());
            String authoritiesJson = jwtUtils.getAuthoritiesToJson(tokenDetails.getClaims());

            tokenDetails.setUserId(userId);
            tokenDetails.setAuthoritiesJson(authoritiesJson);
        }

        return tokenDetails;
    }

}
