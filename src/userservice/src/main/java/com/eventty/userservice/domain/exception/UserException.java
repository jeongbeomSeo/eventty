package com.eventty.userservice.domain.exception;

import com.eventty.userservice.domain.code.ErrorCode;
import lombok.Getter;

@Getter
public class UserException extends RuntimeException{
    private ErrorCode errorCode;
    private String message;

    public UserException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public UserException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
