package com.eventty.userservice.domain.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // Common
    DATA_ACCESS_ERROR   (HttpStatus.INTERNAL_SERVER_ERROR.value(),  "", "Data access error"),
    NOT_FOUND           (HttpStatus.NOT_FOUND.value(), "", "Not Found"),
    METHOD_NOT_ALLOWED  (HttpStatus.METHOD_NOT_ALLOWED.value(),     "", "Method not allowed"),
    INTERNAL_ERROR      (HttpStatus.BAD_REQUEST.value(), "", "Internal server error"),
    INVALID_INPUT_VALUE (HttpStatus.BAD_REQUEST.value(), "", "Invalid input type"),
    INVALID_TYPE_VALUE  (HttpStatus.BAD_REQUEST.value(), "", "Invalid type value"),
    BAD_CREDENTIALS     (HttpStatus.BAD_REQUEST.value(), "", "Bad credentials"),
    INVALID_JSON        (HttpStatus.BAD_REQUEST.value(), "", "JSON parse error"),

    // User
    USER_INFO_NOT_FOUND (HttpStatus.BAD_REQUEST.value(), "U001", "User information does not exist");

    private final int status;
    private final String code;
    private final String message;
}