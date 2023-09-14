package com.eventty.gateway.global.exception.token;

import com.eventty.gateway.global.exception.ErrorCode;
import com.eventty.gateway.global.exception.JwtException;

public class NoAccessTokenException  extends JwtException {
    public NoAccessTokenException() { super(ErrorCode.NO_ACCESS_TOKEN); }
}
