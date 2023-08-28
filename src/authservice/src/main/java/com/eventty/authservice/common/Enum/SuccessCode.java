package com.eventty.authservice.common.Enum;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SuccessCode {

    // Validation
    EMAIL_IS_VALID(200, "Email is valid"),

    // Create
    USER_CREATED(201, "SignUp successful");

    private final int status;
    private final String message;

}
