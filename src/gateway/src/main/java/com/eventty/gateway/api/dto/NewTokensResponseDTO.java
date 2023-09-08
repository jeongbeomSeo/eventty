package com.eventty.gateway.api.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class NewTokensResponseDTO {
    private String accessToken;
    private String refreshToken;

}
