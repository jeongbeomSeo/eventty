package com.eventty.userservice.domain.exception;

import com.eventty.userservice.domain.code.ErrorCode;
import lombok.Getter;

@Getter
public class UserException extends RuntimeException{
    private ErrorCode errorCode;

    public UserException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
