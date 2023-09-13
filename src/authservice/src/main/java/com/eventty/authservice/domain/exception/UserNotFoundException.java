package com.eventty.authservice.domain.exception;

import com.eventty.authservice.global.Enum.ErrorCode;
import com.eventty.authservice.global.exception.AuthException;

public class UserNotFoundException extends AuthException {

    public UserNotFoundException(String email) {
        super(ErrorCode.USER_NOT_FOUND, email);
    }

    public UserNotFoundException(Long userId) {
        super(ErrorCode.USER_NOT_FOUND, userId);
    }
}