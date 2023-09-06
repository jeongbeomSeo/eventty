package com.eventty.userservice.presentation.dto;

import com.eventty.userservice.domain.code.SuccessCode;
import lombok.*;
import org.springframework.http.ResponseEntity;

@Setter @Getter
public class SuccessResponseDTO<T> extends ResponseDTO {
    private T data;

    private SuccessResponseDTO(T data, SuccessCode successCode){
        super(true, "0", successCode.getMessage());
        this.data = data;
    }

    public static <T> SuccessResponseDTO<T> of(T data, SuccessCode successCode){
        return new SuccessResponseDTO<>(data, successCode);
    }
}
