package com.eventty.userservice.presentation.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

@Getter
@RequiredArgsConstructor
public class ResponseDTO {
    private final Boolean success;
    private final String code;
    private final String message;
}