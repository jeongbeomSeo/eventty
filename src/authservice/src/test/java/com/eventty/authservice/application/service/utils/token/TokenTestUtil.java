package com.eventty.authservice.application.service.utils.token;

import com.eventty.authservice.applicaiton.dto.SessionTokensDTO;
import com.eventty.authservice.applicaiton.service.utils.token.TokenEnum;
import com.eventty.authservice.domain.entity.CsrfTokenEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.time.Duration;
import java.util.Date;
import java.util.Optional;

public class TokenTestUtil {
    public static final String ISSUER = "ISSUER";
    public static final String SECRET_KEY = "secret key";
    public static final Long VALID_EXPIRED_TIME = 2L;
    public static final Long UNVALID_EXPIRED_TIME = -10L;

    public static Optional<CsrfTokenEntity> createCsrfTokenEntity(Long id, Long userId, String name) {
        return Optional.of(CsrfTokenEntity.builder()
                .id(id)
                .userId(userId)
                .name(name)
                .build());
    }
    public static SessionTokensDTO createExpiredSessionTokensDTO(String email, Long userId) {
        return new SessionTokensDTO(createToken(email, userId, UNVALID_EXPIRED_TIME), createToken(email, userId, UNVALID_EXPIRED_TIME));
    }
    public static SessionTokensDTO createUpdateSessionTokensDTO(String email, Long userId) {
        return new SessionTokensDTO(createToken(email, userId, UNVALID_EXPIRED_TIME), createToken(email, userId, VALID_EXPIRED_TIME));
    }
    public static SessionTokensDTO createSessionTokensDTO(String email, Long userId) {
        return new SessionTokensDTO(createToken(email, userId, VALID_EXPIRED_TIME), createToken(email, userId, VALID_EXPIRED_TIME));
    }

    private static String createToken(String email, Long userId, Long expired_time) {
        Claims claims = Jwts.claims().setSubject(email);

        claims.put(TokenEnum.USERID.getName(), userId);

        Date now = new Date();
        Duration expiredAt = Duration.ofHours(expired_time);

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setHeaderParam("alg", "HS256")
                .addClaims(claims)
                .setIssuer(ISSUER)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + expiredAt.toMillis()))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }
}
