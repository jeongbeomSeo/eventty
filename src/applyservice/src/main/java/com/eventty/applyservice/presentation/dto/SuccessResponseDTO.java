package com.eventty.applyservice.presentation.dto;

import lombok.Getter;

@Getter
public class SuccessResponseDTO<T>{

    // code 번호 0으로 고정
    private final T data;

    private SuccessResponseDTO(T data) {
        this.data = data;
    }

    public static <T> SuccessResponseDTO<T> of(T data) {
        return new SuccessResponseDTO<>(data);
    }

}