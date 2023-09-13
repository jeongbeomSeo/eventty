package com.eventty.businessservice.common.Enum;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // Common
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

    // Event
    EVENT_NOT_FOUND     (HttpStatus.NOT_FOUND.value(), "E001", "Event is not found"),
    CATEGORY_NOT_FOUND  (HttpStatus.NOT_FOUND.value(), "E003", "Category is not found");


    private final int status;
    private final String code;
    private final String message;

}