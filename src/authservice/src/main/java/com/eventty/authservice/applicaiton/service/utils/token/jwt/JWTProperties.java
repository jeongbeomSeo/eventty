package com.eventty.authservice.applicaiton.service.utils.token.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties("jwt")
public class JWTProperties {
    private String issuer;
    private String secretKey;
}
