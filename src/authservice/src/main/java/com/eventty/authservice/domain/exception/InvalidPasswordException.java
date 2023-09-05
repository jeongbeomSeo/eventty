package com.eventty.authservice.domain.exception;

import com.eventty.authservice.common.Enum.ErrorCode;
import com.eventty.authservice.common.exception.AuthException;

public class InvalidPasswordException extends AuthException {

    public final static InvalidPasswordException EXCEPTION = new InvalidPasswordException();
    private InvalidPasswordException() {
        super(ErrorCode.INVALID_INPUT_VALUE);
    }
}