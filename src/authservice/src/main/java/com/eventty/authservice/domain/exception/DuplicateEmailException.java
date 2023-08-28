package com.eventty.authservice.domain.exception;

import com.eventty.authservice.common.Enum.ErrorCode;
import com.eventty.authservice.common.exception.AuthException;

public class DuplicateEmailException extends AuthException {

    public DuplicateEmailException() {
        super(ErrorCode.DUPLICATE_EMAIL);
    }
}
