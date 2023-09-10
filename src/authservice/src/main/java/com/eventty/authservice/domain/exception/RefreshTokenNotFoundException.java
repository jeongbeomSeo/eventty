package com.eventty.authservice.domain.exception;

import com.eventty.authservice.global.Enum.ErrorCode;
import com.eventty.authservice.global.exception.AuthException;

public class RefreshTokenNotFoundException extends AuthException {
    public final static RefreshTokenNotFoundException EXCEPTION = new RefreshTokenNotFoundException();
    private RefreshTokenNotFoundException() {
        // Customizing 할지, 그냥 할지 고민
        super(ErrorCode.REFRESH_TOKEN_NOT_FOUND);
    }
}
