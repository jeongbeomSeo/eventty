package com.eventty.gateway.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // Common
    // 나중에 필요 없는 것 주석 처리할 예정
    INTERNAL_ERROR(500, "", "Internal server error"),
    NOT_FOUND(404, "", "Not Found"),
    DATA_NOT_FOUND(404, "", "Data not found"),
    METHOD_NOT_ALLOWED(405, "", "Method not allowed"),
    INVALID_INPUT_VALUE(400, "", "Invalid input type"),
    INVALID_TYPE_VALUE(400, "", "Invalid type value"),
    BAD_CREDENTIALS(400, "", "Bad credentials"),
    DATABASE_CONSTRAINT_VIOLATION(400, "", "Database constraint violation"),
    REFERENCE_INTEGRITY_VIOLATION(400, "" , "Reference integrity violation"),
    DATA_SIZE_VIOLATION(400, "", "Data size exceeds limit"),
    CONFLICT(409, "", "Conflict occurred"),

    // Authenticaiton
    FAIL_AUTHENTICATION(400, "G000", "Fail Authentication"),
    ACCESSTOKEN_IS_EMPTY(403, "G000", "AccessToken is empty"),
    REFRESHTOKEN_IS_EMPTY(403, "G000", "RefreshToken is empty"),
    REFRESHTOKEN_EXPIRED(403, "G000", "RefreshToken is expired"),
    FAIL_GET_NEW_TOKENS(400, "G000", "Failed to get new tokens"),
    INVALID_ISSUER(403, "G000", "Invalid Issuer");

    private final int status;
    private final String code;
    private final String message;
}
