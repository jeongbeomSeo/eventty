package com.eventty.authservice.applicaiton.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class TokensDTO {
    private String accessToken;
    private String refreshToken;
}
