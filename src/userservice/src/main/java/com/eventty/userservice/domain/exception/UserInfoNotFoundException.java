package com.eventty.userservice.domain.exception;

import com.eventty.userservice.domain.code.ErrorCode;

public class UserInfoNotFoundException extends UserException {
    private ErrorCode errorCode;
    private String message;

    public final static UserException EXCEPTION = new UserInfoNotFoundException();

    private UserInfoNotFoundException(){
        super(ErrorCode.USER_INFO_NOT_FOUND);
    }
}