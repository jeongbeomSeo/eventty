package com.eventty.authservice.presentation.dto.request;

import jakarta.validation.constraints.Email;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record FullUserCreateRequestDTO (
        @NotNull @Email
        String email,
        @NotNull
        String password,
        @NotNull
        String name,
        @NotNull
        String phone,
        LocalDate birth,
        String address
){ }
