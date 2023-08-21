package com.eventty.businessservice.common.dto;

import com.eventty.businessservice.common.SuccessCode;
import lombok.Getter;

/** 성공 시 응답 객체 예시
{
    "success": true,
    "code": "E001,
    "message": "OK",
    "data": [
        1,
        2,
        3
    ]
}
 */

@Getter
public class SuccessResponseDTO<T> extends ResponseDTO {

    private final T data;

    private SuccessResponseDTO(T data, SuccessCode successCode) {
        super(true, successCode.getCode(), successCode.getMessage());
        this.data = data;
    }

    public static <T> SuccessResponseDTO<T> of(T data, SuccessCode successCode) {
        return new SuccessResponseDTO<>(data, successCode);
    }
}