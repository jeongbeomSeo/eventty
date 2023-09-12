package com.eventty.authservice.global.Enum;

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
    PERMISSION_DENIED(403, "", "Permission Denided"),

    // Auth
    DUPLICATE_EMAIL(409, "", "Duplicate Email"),
    USER_NOT_FOUND(404, "", "User Not Found Exception"),
    INVALID_PASSWORD(400, "", "Invalid Password Exception"),
    INVALID_REFRESH_TOKEN(403, "", "Invalid Refresh Token Exception"),
    REFRESH_TOKEN_NOT_FOUND(404, "", "Refresh Token Not Found Exception"),
    ACCESS_DELETED_USER(400, "", "User has been deactivated.");

    private final int status;
    private final String code;
    private final String message;
}
