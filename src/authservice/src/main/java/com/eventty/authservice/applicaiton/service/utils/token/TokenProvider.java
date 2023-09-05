package com.eventty.authservice.applicaiton.service.utils.token;

import com.eventty.authservice.applicaiton.dto.TokenDTO;
import com.eventty.authservice.applicaiton.service.utils.token.jwt.AccessTokenProvider;
import com.eventty.authservice.domain.entity.AuthUserEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Date;

@Component
@AllArgsConstructor
public class TokenProvider {

    private final AccessTokenProvider accessTokenProvider;

    private final RefreshTokenProvider refreshTokenProvider;

    public TokenDTO getAllToken(AuthUserEntity authUserEntity) {

        Date now = new Date();

        // Access Token의 만료 시간: 2시간
        String accessToken = accessTokenProvider.generateToken(authUserEntity, now, createExpiry(now, Duration.ofHours(2)));

        // Refresh Token의 만교 기간: 2일
        String refreshToken = refreshTokenProvider.generateToken(authUserEntity, now, createExpiry(now, Duration.ofDays(2L)));

        // Refresh Token 저장
        refreshTokenProvider.saveRefreshToken(refreshToken, authUserEntity.getId());

        return TokenDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }



    private Date createExpiry(Date now, Duration expiredAt) {
        return new Date(now.getTime() + expiredAt.toMillis());
    }


}
