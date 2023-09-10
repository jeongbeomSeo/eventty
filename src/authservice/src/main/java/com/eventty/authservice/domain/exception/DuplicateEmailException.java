package com.eventty.authservice.domain.exception;

import com.eventty.authservice.global.Enum.ErrorCode;
import com.eventty.authservice.global.exception.AuthException;

public class DuplicateEmailException extends AuthException {

    public final static DuplicateEmailException EXCEPTION = new DuplicateEmailException();
    private DuplicateEmailException() {
        super(ErrorCode.DUPLICATE_EMAIL);
    }
}
