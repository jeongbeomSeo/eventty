package com.eventty.authservice.global.exception;

import com.eventty.authservice.global.Enum.ErrorCode;
import lombok.Getter;

@Getter
public class AuthException extends RuntimeException{

    private ErrorCode errorCode;

    protected AuthException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
