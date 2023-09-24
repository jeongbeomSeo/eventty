package com.eventty.authservice.api.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GoogleTokenResponseDTO {
    private String accessToken;
    private String expiresIn;
    private String scope;
    private String tokenType;
    // private String idToken;
}
