package com.eventty.authservice.api.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class NaverTokenResponseDTO {
    private String accessToken;
    private String refreshToken;
    private String tokenType;
    private Integer expiresIn;
}
