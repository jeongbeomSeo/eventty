package com.eventty.authservice.domain.exception;

import com.eventty.authservice.global.Enum.ErrorCode;
import com.eventty.authservice.global.exception.AuthException;

public class DuplicateEmailException extends AuthException {

    public DuplicateEmailException(String email) {
        super(ErrorCode.DUPLICATE_EMAIL, email);
    }
}
