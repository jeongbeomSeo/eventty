package com.eventty.businessservice.common.Enum;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // Common
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "C000", "Internal server error"),
    DATA_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "C001", "Data not found"),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED.value(), "C002", "Method not allowed"),
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST.value(), "C003", "Invalid input type"),
    INVALID_TYPE_VALUE(HttpStatus.BAD_REQUEST.value(), "C004", "Invalid type value"),
    BAD_CREDENTIALS(HttpStatus.BAD_REQUEST.value(), "C005", "Bad credentials"),
    DATABASE_CONSTRAINT_VIOLATION(HttpStatus.CONFLICT.value(), "C006", "Database constraint violation"),
    REFERENCE_INTEGRITY_VIOLATION(HttpStatus.BAD_REQUEST.value(), "C007", "Reference integrity violation"),
    DATA_SIZE_VIOLATION(HttpStatus.BAD_REQUEST.value(), "C008", "Data size exceeds limit"),
    CONFLICT(HttpStatus.CONFLICT.value(), "C009", "Conflict occurred"),


    // Event
    EVENT_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "E001", "Event is not found"),
    EVENT_ALREADY_EXIST(HttpStatus.CONFLICT.value(), "E002", "Event already exists"),
    CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "E003", "Category is not found"),

    // Apply
    // 수정 예정
    APPLY_CONFLICT(HttpStatus.CONFLICT.value(), "A001", "Reservation conflict detected"),

    // Auth
    // 수정 예정
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED.value(), "U001", "User unauthorized");

    private final int status;
    private final String code;
    private final String message;

}