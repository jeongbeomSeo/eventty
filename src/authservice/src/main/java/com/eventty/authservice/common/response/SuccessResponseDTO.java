package com.eventty.authservice.common.response;

import lombok.Getter;

import com.eventty.authservice.common.Enum.SuccessCode;

/** 성공 시 응답 객체 예시
 {
 "success": true,
 "code": "0",
 "message": "OK",
 "data": [
 1,
 2,
 3
 ]
 }
 */


@Getter
public class SuccessResponseDTO<T> extends ResponseDTO{

    // code 번호 0으로 고정
    private final T data;

    public SuccessResponseDTO(T data, SuccessCode successCode) {
        super(true, "0", successCode.getMessage());
        this.data = data;
    }

    public static <T> SuccessResponseDTO<T> of(T data, SuccessCode successCode) {
        return new SuccessResponseDTO<>(data, successCode);
    }

}
