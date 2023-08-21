package com.eventty.businessservice.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SuccessCode {

    // EVENT
    GET_EVENT_INFO_SUCCESS(200, "E001", "Event retrieved successfully"),
    CREATE_EVENT_SUCCESS(201, "E002", "Event created successfully"),
    UPDATE_EVENT_SUCCESS(200, "E003", "Event updated successfully"),
    DELETE_EVENT_SUCCESS(200, "E004", "Event deleted successfully"),

    // APPLY
    // 수정 예정
    APPLY_SUCCESS(201, "A001", "Apply successful"),
    CANCEL_APPLY_SUCCESS(200, "A002", "Apply cancelled successfully"),
    MODIFY_APPLY_SUCCESS(200, "A003", "Apply modified successfully");

    private final int status;
    private final String code;
    private final String message;

}
