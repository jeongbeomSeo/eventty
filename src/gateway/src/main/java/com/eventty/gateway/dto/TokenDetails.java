package com.eventty.gateway.dto;

import com.eventty.gateway.utils.jwt.Authority;
import io.jsonwebtoken.Claims;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class TokenDetails {
    private String accessToken;
    private String refreshToken;
    private Claims claims;
    private String userId;
    private String authoritiesJson;
    private boolean needsUpdate;
}
