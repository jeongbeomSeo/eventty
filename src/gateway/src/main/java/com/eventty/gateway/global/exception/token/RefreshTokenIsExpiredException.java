package com.eventty.gateway.global.exception.token;

import com.eventty.gateway.global.exception.ErrorCode;
import com.eventty.gateway.global.exception.JwtException;

public class RefreshTokenIsExpiredException extends JwtException {

    public final static RefreshTokenIsExpiredException EXCEPTION = new RefreshTokenIsExpiredException();
    private RefreshTokenIsExpiredException() { super(ErrorCode.REFRESHTOKEN_EXPIRED); }
}
