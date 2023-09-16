package com.eventty.gateway.global.exception.token;

import com.eventty.gateway.global.exception.ErrorCode;
import com.eventty.gateway.global.exception.JwtException;

public class NoCsrfTokenException extends JwtException{
    public NoCsrfTokenException() { super(ErrorCode.NO_CSRF_TOKEN); }

}
