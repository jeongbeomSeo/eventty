package com.eventty.authservice.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseDTO {
    private final Boolean success;
    private final String code;
    private final String message;
}

