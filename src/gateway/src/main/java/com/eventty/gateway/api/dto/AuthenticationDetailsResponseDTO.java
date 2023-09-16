package com.eventty.gateway.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AuthenticationDetailsResponseDTO(
        @JsonProperty("userId") Long userId,
        @JsonProperty("accessToken") String accessToken,
        @JsonProperty("refreshToken") String refreshToken,
        @JsonProperty("csrfToken") String csrfToken,
        @JsonProperty("authorities") String authoritiesJSON,
        @JsonProperty("needsUpdate") boolean needsUpdate
) {}
