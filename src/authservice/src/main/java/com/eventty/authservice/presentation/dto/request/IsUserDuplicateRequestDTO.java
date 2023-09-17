package com.eventty.authservice.presentation.dto.request;

import jakarta.validation.constraints.Email;
import lombok.*;
public record IsUserDuplicateRequestDTO (
    @Email
    String email
){ }
