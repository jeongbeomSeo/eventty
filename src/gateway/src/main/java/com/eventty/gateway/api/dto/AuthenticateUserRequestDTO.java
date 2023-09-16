package com.eventty.gateway.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AuthenticateUserRequestDTO(
        @JsonProperty("accessToken") String accessToken,
        @JsonProperty("refreshToken") String refreshToken,
        @JsonProperty("csrfToken") String csrfToken
) {}
