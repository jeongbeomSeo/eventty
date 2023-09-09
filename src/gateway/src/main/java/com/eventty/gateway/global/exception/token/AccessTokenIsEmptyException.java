package com.eventty.gateway.global.exception.token;

import com.eventty.gateway.global.exception.ErrorCode;
import com.eventty.gateway.global.exception.JwtException;

public class AccessTokenIsEmptyException extends JwtException {

    public final static AccessTokenIsEmptyException EXCEPTION = new AccessTokenIsEmptyException();

    private AccessTokenIsEmptyException() { super(ErrorCode.ACCESSTOKEN_IS_EMPTY); }
}
