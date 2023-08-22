package com.eventty.businessservice.common.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ResponseDTO {
    private final Boolean success;
    private final String code;
    private final String message;
}
