package com.eventty.userservice.domain.exception;

import com.eventty.userservice.domain.code.ErrorCode;

public class DuplicateUserIdException extends UserException{
    public final static UserException EXCEPTION = new DuplicateUserIdException();

    private DuplicateUserIdException(){
        super(ErrorCode.USER_ID_DUPLICATE);
    }
}
