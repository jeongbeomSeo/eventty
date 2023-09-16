package com.eventty.authservice.applicaiton.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AuthenticationDetailsDTO(
         Long userId,
         String accessToken,
         String refreshToken,
         String csrfToken,
         boolean needsUpdate
) {
}
