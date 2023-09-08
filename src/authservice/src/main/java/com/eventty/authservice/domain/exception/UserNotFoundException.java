package com.eventty.authservice.domain.exception;

import com.eventty.authservice.global.Enum.ErrorCode;
import com.eventty.authservice.global.exception.AuthException;

public class UserNotFoundException extends AuthException {

    public final static UserNotFoundException EXCEPTION = new UserNotFoundException();
    private UserNotFoundException() {
        super(ErrorCode.NOT_FOUND);
    }
}