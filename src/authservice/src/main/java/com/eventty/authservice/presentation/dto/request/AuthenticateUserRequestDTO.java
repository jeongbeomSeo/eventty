package com.eventty.authservice.presentation.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AuthenticateUserRequestDTO {
    private String accessToken;
    private String refreshToken;
    private String csrfToken;
}
