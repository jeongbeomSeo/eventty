package com.eventty.gateway.global.exception.token;

import com.eventty.gateway.global.exception.ErrorCode;
import com.eventty.gateway.global.exception.JwtException;

public class FailGetNewTokensException extends JwtException {
    public final static FailGetNewTokensException EXCEPTION = new FailGetNewTokensException();
    private FailGetNewTokensException() { super(ErrorCode.FAIL_GET_NEW_TOKENS); }
}
