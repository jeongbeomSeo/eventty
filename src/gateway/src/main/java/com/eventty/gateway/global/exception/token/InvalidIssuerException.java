package com.eventty.gateway.global.exception.token;

import com.eventty.gateway.global.exception.ErrorCode;
import com.eventty.gateway.global.exception.JwtException;

public class InvalidIssuerException extends JwtException {
    public final static InvalidIssuerException EXCEPTION = new InvalidIssuerException();
    private InvalidIssuerException() { super(ErrorCode.FAIL_GET_NEW_TOKENS); }
}
