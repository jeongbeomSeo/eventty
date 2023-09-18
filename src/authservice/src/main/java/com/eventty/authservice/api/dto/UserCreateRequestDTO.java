package com.eventty.authservice.api.dto;

import lombok.*;

import java.time.LocalDate;

/*
 User Server로 보낼 DTO
 */
public record UserCreateRequestDTO (
        Long userId,
        String name,
        String address,
        LocalDate birth,
        String phone
) { }
