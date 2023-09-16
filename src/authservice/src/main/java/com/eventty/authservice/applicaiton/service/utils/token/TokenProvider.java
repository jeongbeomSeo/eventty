package com.eventty.authservice.applicaiton.service.utils.token;

import com.eventty.authservice.applicaiton.dto.TokenParsingDTO;
import com.eventty.authservice.applicaiton.dto.TokensDTO;
import com.eventty.authservice.domain.entity.AuthUserEntity;
import com.eventty.authservice.domain.entity.CsrfTokenEntity;
import com.eventty.authservice.domain.exception.CsrfTokenNotFoundException;
import com.eventty.authservice.domain.exception.InValidRefreshTokenException;
import com.eventty.authservice.presentation.dto.request.AuthenticationUserRequestDTO;
import com.eventty.authservice.presentation.dto.request.GetNewTokensRequestDTO;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
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
    private final CSRFTokenProvider csrfTokenProvider;

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
            throw new InValidRefreshTokenException(getNewTokensRequestDTO);
    }

    // Token Parsing해서 User Id와 needsUpdate 반환 (1차 검증)
    public TokenParsingDTO parsingToken(AuthenticationUserRequestDTO authenticationUserRequestDTO) {

        // JWT를 이용해서 JWT Cliams 가져오기
        Claims claims = getClaimsOrNullOnExpiration(authenticationUserRequestDTO.accessToken());

        // 만약 만료 기간이 지났다면,
        if (claims == null) {

            // Refresh Token 이용해서 Claims update
            claims = getClaimsOrThrow(authenticationUserRequestDTO.refreshToken());

            // User ID 가져오기
            Long userId = getUserId(claims);

            // Refresh Token Validation Check
            refreshTokenProvider.validationCheck(userId, authenticationUserRequestDTO.refreshToken());

            // 새로 업데이트 해주기
            return new TokenParsingDTO(userId, true);
        }
        return new TokenParsingDTO(getUserId(claims), false);
    }

    public String getCsrfToken(Long userId) {
        // User Id 이용해서 저장되어 있는 CSRF Token 가저오기
        return csrfTokenProvider.getCsrfTokenByUserId(userId)
                .orElseThrow(() -> new CsrfTokenNotFoundException(userId))
                .getName();
    }

    public String updateCsrfToken(Long userId) {
        return csrfTokenProvider.update(userId);
    }

    public String saveCsrfToken(Long userId) {
        return csrfTokenProvider.save(userId);
    }

    // 토큰 Proivder에 두어서 직접 파싱
    // 만료 기간이 지난 경우에는 예외
    private Claims getClaimsOrNullOnExpiration(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(tokenProperties.getSecretKey())
                    .requireIssuer(tokenProperties.getIssuer())
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            return null;
        }
        return claims;
    }

    // 서명이 유효하지 않은 경우, 만료 기간 지난 경우, 파싱 실패 전부 예외 터트리기
    private Claims getClaimsOrThrow(String token) {
        return Jwts.parser()
                .setSigningKey(tokenProperties.getSecretKey())
                .requireIssuer(tokenProperties.getIssuer())
                .parseClaimsJws(token)
                .getBody();
    }

    private Long getUserId(Claims claims) {
        return Long.parseLong(claims.get(TokenEnum.USERID.getName()).toString());
    }

    private Date createExpiry(Date now, Duration expiredAt) {
        return new Date(now.getTime() + expiredAt.toMillis());
    }
}
