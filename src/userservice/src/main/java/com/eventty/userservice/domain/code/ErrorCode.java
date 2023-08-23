package com.eventty.userservice.domain.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // Common
    INTERNAL_ERROR(500, "C101", "Internal server error"),
    DATA_ACCESS_ERROR(500, "C100", "Data access error"),
    INVALID_INPUT_VALUE(400, "C102", "Invalid input type"),
    METHOD_NOT_ALLOWED(405, "C103", "Method not allowed"),
    INVALID_TYPE_VALUE(400, "C104", "Invalid type value"),
    BAD_CREDENTIALS(400, "1005", "Bad credentials"),
    NOTNULL_PROPERTY_ERROR(400, "C106", "not-null property references a null"),

    USER_INFO_NOT_FOUND(400, "U001", "User information does not exist"),
    // 이건 아직 안쓰임 - 차후 수정
    DUPLICATE_LOGIN_ID(400, "U002", "계정명이 중복된 경우");

    private final int status;
    private final String code;
    private final String message;
}