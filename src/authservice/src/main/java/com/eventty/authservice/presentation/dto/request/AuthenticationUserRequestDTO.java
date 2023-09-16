package com.eventty.authservice.presentation.dto.request;

public record AuthenticationUserRequestDTO(
        String accessToken,
        String refreshToken,
        String csrfToken
) {
}
