package com.eventty.gateway.global.exception.token;

import com.eventty.gateway.global.exception.ErrorCode;
import com.eventty.gateway.global.exception.JwtException;

public class RefreshTokenIsEmptyException extends JwtException {

    public final static RefreshTokenIsEmptyException EXCEPTION = new RefreshTokenIsEmptyException();

    private RefreshTokenIsEmptyException() { super(ErrorCode.REFRESHTOKEN_IS_EMPTY); }
}
