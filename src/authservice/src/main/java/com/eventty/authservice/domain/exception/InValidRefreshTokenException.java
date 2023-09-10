package com.eventty.authservice.domain.exception;

import com.eventty.authservice.global.Enum.ErrorCode;
import com.eventty.authservice.global.exception.AuthException;

public class InValidRefreshTokenException extends AuthException {
    public final static InValidRefreshTokenException EXCEPTION = new InValidRefreshTokenException();
    private InValidRefreshTokenException() {
        // Customizing 할지, 그냥 할지 고민 => 아마도 공통 Code사용해서 가져가야 받아서 사용 가능할 것 같음(일단 보류)
        super(ErrorCode.INVALID_REFRESH_TOKEN);
    }
}