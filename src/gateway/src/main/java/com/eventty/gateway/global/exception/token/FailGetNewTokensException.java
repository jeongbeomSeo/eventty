package com.eventty.gateway.global.exception.token;

import com.eventty.gateway.global.exception.ErrorCode;
import com.eventty.gateway.global.exception.JwtException;

public class FailGetNewTokensException extends JwtException {
    private static final String[] fields = {"userId"};
    public FailGetNewTokensException(String userId) { super(ErrorCode.FAIL_GET_NEW_TOKENS, userId, fields); }
}
