package com.eventty.authservice.applicaiton.service.utils.token;

import com.eventty.authservice.applicaiton.service.utils.token.jwt.JWTProperties;
import com.eventty.authservice.domain.entity.AuthUserEntity;
import com.eventty.authservice.domain.entity.RefreshTokenEntity;
import com.eventty.authservice.domain.repository.RefreshTokenRepository;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Date;
import java.util.Optional;

@Component
@AllArgsConstructor
public class RefreshTokenProvider {

    private final JWTProperties jwtProperties;
    private final RefreshTokenRepository refreshTokenRepository;

    // 나중에 게이트웨이에서 JWT Parsing하고난 후 id랑 refresh Token 보내온 것을 토대로 꺼내와서 검사
    public Optional<RefreshTokenEntity> findByRefreshToken(Long userId) {
        return refreshTokenRepository.findByUserId(userId);
    }

    public String generateToken(AuthUserEntity authUserEntity, Date now, Date expiry) {
        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setHeaderParam("alg", "HS256")
                .setIssuer(jwtProperties.getIssuer())
                .setIssuedAt(now)
                .setExpiration(expiry)
                .setSubject(authUserEntity.getEmail())
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())
                .compact();
    }

    public RefreshTokenEntity saveRefreshToken(String refreshToken, Long userId) {
        RefreshTokenEntity refreshTokenEntity = RefreshTokenEntity.builder()
                .userId(userId)
                .refreshToken(refreshToken)
                .build();

        RefreshTokenEntity savedRefreshTokenEntity =  refreshTokenRepository.save(refreshTokenEntity);

        return savedRefreshTokenEntity;
    }

    private Date createExpiry(Date now, Duration expiredAt) {
        return new Date(now.getTime() + expiredAt.toMillis());
    }

}
