package com.eventty.gateway.global.exception;

import lombok.Getter;

@Getter
public class JwtException extends RuntimeException{

    private final ErrorCode errorCode;

    protected JwtException(ErrorCode errorCode) { this.errorCode = errorCode; }
}
