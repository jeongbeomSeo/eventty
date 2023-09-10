package com.eventty.businessservice.common.Enum;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SuccessCode {

    // EVENT
    GET_EVENT_INFO_SUCCESS(200, "Event retrieved successfully"),
    CREATE_EVENT_SUCCESS(201, "Event created successfully"),
    UPDATE_EVENT_SUCCESS(200, "Event updated successfully"),
    DELETE_EVENT_SUCCESS(200, "Event deleted successfully"),

    // APPLY
    // 수정 예정
    APPLY_SUCCESS(201, "Apply successful"),
    CANCEL_APPLY_SUCCESS(200, "Apply cancelled successfully"),
    MODIFY_APPLY_SUCCESS(200, "Apply modified successfully");

    private final int status;
    private final String message;

}
