package com.eventty.gateway.global.exception;

import lombok.Getter;

@Getter
public class JwtException extends RuntimeException{

    private ErrorCode errorCode;
    private Object causedErrorData;
    private String[] fields;

    protected JwtException(ErrorCode errorCode) { this.errorCode = errorCode; }

    protected JwtException(ErrorCode errorCode, Object cuasedErrorData, String[] fields) {
        this.errorCode = errorCode;
        this.causedErrorData = cuasedErrorData;
        this.fields = fields;
    }
}
