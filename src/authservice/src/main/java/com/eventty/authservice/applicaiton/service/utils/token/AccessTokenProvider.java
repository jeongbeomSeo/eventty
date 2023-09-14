package com.eventty.authservice.applicaiton.service.utils.token;

import com.eventty.authservice.domain.entity.AuthUserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AccessTokenProvider {

    private final TokenProperties tokenProperties;

    public String generateToken(AuthUserEntity authUserEntity, Date now, Date expiry) {

        Claims claims = Jwts.claims().setSubject(authUserEntity.getEmail());
        String USER_ID = "userId";
        String AUTHORITIES = "authorities";

        claims.put(USER_ID, authUserEntity.getId());
        claims.put(AUTHORITIES, getAuthorities(authUserEntity));

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setHeaderParam("alg", "HS256")
                .addClaims(claims)
                .setIssuer(tokenProperties.getIssuer())
                .setIssuedAt(now)
                .setExpiration(expiry)
                .setSubject(authUserEntity.getEmail())
                .signWith(SignatureAlgorithm.HS256, tokenProperties.getSecretKey())
                .compact();
    }

    private Collection<? extends GrantedAuthority> getAuthorities(AuthUserEntity authUserEntity) {
        return authUserEntity.getAuthorities().stream()
                .map(authorityEntity -> new SimpleGrantedAuthority(
                        authorityEntity.getName()
                )).collect(Collectors.toList());
    }
}
