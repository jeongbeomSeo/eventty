package com.eventty.gateway.api.dto;

public class AuthenticationDetailsResponseDTO {
    Long userId;
    String accessToken;
    String refreshToken;
    String csrfToken;
    boolean needsUpdate;
}
