package com.eventty.authservice.presentation.dto.request;

import jakarta.validation.constraints.NotNull;

public record ChangePWRequestDTO (
        @NotNull
        String password
) {}
