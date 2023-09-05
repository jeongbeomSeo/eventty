package com.eventty.authservice.domain.exception;

import com.eventty.authservice.common.Enum.ErrorCode;
import com.eventty.authservice.common.exception.AuthException;

public class UserNotFoundException extends AuthException {

    public final static UserNotFoundException EXCEPTION = new UserNotFoundException();
    private UserNotFoundException() {
        super(ErrorCode.NOT_FOUND);
    }
}