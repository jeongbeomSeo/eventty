package com.eventty.authservice.presentation.dto.response;

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
