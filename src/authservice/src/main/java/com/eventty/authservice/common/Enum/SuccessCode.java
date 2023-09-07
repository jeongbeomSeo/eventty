package com.eventty.authservice.common.Enum;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SuccessCode {

    // OK
    IS_OK(200),

    // Create
    USER_CREATED(201);

    private final int status;
}