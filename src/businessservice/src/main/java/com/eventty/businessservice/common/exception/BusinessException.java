package com.eventty.businessservice.common.exception;

import com.eventty.businessservice.common.ErrorCode;
import com.eventty.businessservice.common.dto.ErrorResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@Getter
@AllArgsConstructor
public class BusinessException extends RuntimeException {

    private ErrorCode errorCode;
    private List<ErrorResponseDTO.FieldError> errors = new ArrayList<>();

    public BusinessException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
