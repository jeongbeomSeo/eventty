package com.eventty.businessservice.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SuccessCode {

    // EVENT
    GET_EVENT_INFO_SUCCESS(200, "E001", "Event retrieved successfully"),
    CREATE_EVENT_SUCCESS(201, "E002", "Event created successfully");

    private final int status;
    private final String code;
    private final String message;

}
