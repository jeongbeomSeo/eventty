package com.eventty.authservice.common.exception;

import lombok.Getter;

import com.eventty.authservice.common.Enum.ErrorCode;

@Getter
public class AuthException extends RuntimeException{

    private ErrorCode errorCode;

    protected AuthException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
