package com.eventty.userservice.domain.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // Common
    DATA_ACCESS_ERROR   (HttpStatus.INTERNAL_SERVER_ERROR.value(),  "C101", "Data access error"),
    METHOD_NOT_ALLOWED  (HttpStatus.METHOD_NOT_ALLOWED.value(),     "C103", "Method not allowed"),
    INTERNAL_ERROR      (HttpStatus.BAD_REQUEST.value(), "C100", "Internal server error"),
    INVALID_INPUT_VALUE (HttpStatus.BAD_REQUEST.value(), "C102", "Invalid input type"),
    INVALID_TYPE_VALUE  (HttpStatus.BAD_REQUEST.value(), "C104", "Invalid type value"),
    BAD_CREDENTIALS     (HttpStatus.BAD_REQUEST.value(), "1005", "Bad credentials"),

    // User
    USER_INFO_NOT_FOUND (HttpStatus.BAD_REQUEST.value(), "U001", "User information does not exist");

    private final int status;
    private final String code;
    private final String message;
}