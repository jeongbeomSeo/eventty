package com.eventty.authservice.common.Enum;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // Common
    // 나중에 필요 없는 것 주석 처리할 예정
    INTERNAL_ERROR(500, "C000", "Internal server error"),
    DATA_NOT_FOUND(404, "C001", "Data not found"),
    METHOD_NOT_ALLOWED(405, "C002", "Method not allowed"),
    INVALID_INPUT_VALUE(400, "C003", "Invalid input type"),
    INVALID_TYPE_VALUE(400, "C004", "Invalid type value"),
    BAD_CREDENTIALS(400, "C005", "Bad credentials"),
    DATABASE_CONSTRAINT_VIOLATION(400, "C006", "Database constraint violation"),
    REFERENCE_INTEGRITY_VIOLATION(400, "C007", "Reference integrity violation"),
    DATA_SIZE_VIOLATION(400, "C008", "Data size exceeds limit"),
    CONFLICT(409, "C009", "Conflict occurred"),

    // Auth
    DUPLICATE_EMAIL(409, "A000", "Duplicate Email");


    private final int status;
    private final String code;
    private final String message;
}
