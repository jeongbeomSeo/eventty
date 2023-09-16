package com.eventty.authservice.applicaiton.dto;

public record CsrfTokenDTO (Long userId, String value) {
    CsrfTokenDTO(Long userId) {
        this(userId, "");
    }
}
