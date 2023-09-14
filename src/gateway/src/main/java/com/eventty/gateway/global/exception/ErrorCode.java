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

    // 400: 잘못된 요청 401: 유효한 자격 증명 없음 403: 서버에 요청이 전달되었지만, 권한 떄문에 거절
    // Authenticaiton
    FAIL_AUTHENTICATION(400, "G000", "Fail Authentication"),
    ACCESS_TOKEN_IS_EMPTY(401, "G000", "Access Token is empty"),
    REFRESH_TOKEN_IS_EMPTY(401, "G000", "Refresh Token is empty"),
    REFRESH_TOKEN_EXPIRED(401, "G000", "RefreshToken is expired"),
    FAIL_GET_NEW_TOKENS(400, "G000", "Failed to get new tokens"),
    NO_ACCESS_TOKEN(401, "G000", "No Access Token");

    private final int status;
    private final String code;
    private final String message;
}
