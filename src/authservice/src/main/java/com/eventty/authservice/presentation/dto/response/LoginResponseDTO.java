package com.eventty.authservice.presentation.dto.response;

import lombok.*;

import java.util.List;

public record LoginResponseDTO (
        String email,
        String role
){}
