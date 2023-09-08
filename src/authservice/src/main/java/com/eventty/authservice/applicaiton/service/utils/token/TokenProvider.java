package com.eventty.authservice.applicaiton.service.utils.token;

import com.eventty.authservice.applicaiton.dto.TokensDTO;
import com.eventty.authservice.domain.entity.AuthUserEntity;
import com.eventty.authservice.domain.exception.InValidRefreshTokenException;
import com.eventty.authservice.presentation.dto.request.GetNewTokensRequestDTO;
import com.eventty.authservice.presentation.dto.response.NewTokensResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Date;

@Component
@AllArgsConstructor
public class TokenProvider {

    private final TokenProperties tokenProperties;

    private final AccessTokenProvider accessTokenProvider;

    private final RefreshTokenProvider refreshTokenProvider;

    public TokensDTO getAllToken(AuthUserEntity authUserEntity) {

        Date now = new Date();

        // Access Token의 만료 시간: 2시간
        String accessToken = accessTokenProvider.generateToken(authUserEntity, now, createExpiry(now, Duration.ofHours(tokenProperties.getAccessExpiredTime())));

        // Refresh Token의 만교 기간: 2일
        String refreshToken = refreshTokenProvider.generateToken(authUserEntity, now, createExpiry(now, Duration.ofDays(tokenProperties.getRefreshExpiredTime())));

        // Refresh Token 저장 혹은 업데이트
        refreshTokenProvider.saveOrUpdateRefreshToken(refreshToken, authUserEntity.getId());

        return TokensDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public void refreshTokenValidationCheck(GetNewTokensRequestDTO getNewTokensRequestDTO) {

        String existedRefreshToken = refreshTokenProvider.findByRefreshToken(getNewTokensRequestDTO.getUserId());

        if (!getNewTokensRequestDTO.getRefreshToken().equals(existedRefreshToken))
            throw InValidRefreshTokenException.EXCEPTION;
    }
    private Date createExpiry(Date now, Duration expiredAt) {
        return new Date(now.getTime() + expiredAt.toMillis());
    }
}
