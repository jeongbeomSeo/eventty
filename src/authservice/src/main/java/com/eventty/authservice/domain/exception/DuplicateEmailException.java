package com.eventty.authservice.domain.exception;

import com.eventty.authservice.common.Enum.ErrorCode;
import com.eventty.authservice.common.exception.AuthException;

public class DuplicateEmailException extends AuthException {
    private final static String message = "Duplicate Email";
    public final static AuthException EXCEPTION = new DuplicateEmailException();

    private DuplicateEmailException() {
        super(ErrorCode.DUPLICATE_EMAIL, message);
    }
}
