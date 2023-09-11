package com.eventty.authservice.domain.exception;

import com.eventty.authservice.global.Enum.ErrorCode;
import com.eventty.authservice.global.exception.AuthException;

public class AccessDeletedUserException extends AuthException {

    public final static AccessDeletedUserException EXCEPTION = new AccessDeletedUserException();

    private AccessDeletedUserException() {
        super(ErrorCode.ACCESS_DELETED_USER);
    }
}
