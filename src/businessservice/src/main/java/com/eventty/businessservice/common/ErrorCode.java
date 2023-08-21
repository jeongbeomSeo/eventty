package com.eventty.businessservice.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // Common
    INTERNAL_ERROR(500, "C001", "Internal server error"),
    DATA_ACCESS_ERROR(500, "C000", "Data access error"),
    INVALID_INPUT_VALUE(400, "C002", "Invalid input type"),
    METHOD_NOT_ALLOWED(405, "C003", "Method not allowed"),
    INVALID_TYPE_VALUE(400, "C004", "Invalid type value"),
    BAD_CREDENTIALS(400, "C005", "Bad credentials"),

    // Event
    EVENT_NOT_FOUND(404, "E001", "Event is not found"),
    EVENT_ALREADY_EXIST(409, "E002", "Event already exists"),
    EVENT_DATE_CONFLICT(400, "E003", "Event date conflicts with another event"),

    // Apply
    // 수정 예정
    APPLY_CONFLICT(409, "B001", "Reservation conflict detected"),

    // Auth
    // 수정 예정
    UNAUTHORIZED(401, "M001", "User unauthorized");

    private final int status;
    private final String code;
    private final String message;

}