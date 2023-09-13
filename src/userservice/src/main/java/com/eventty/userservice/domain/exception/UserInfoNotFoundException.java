package com.eventty.userservice.domain.exception;

import com.eventty.userservice.domain.UserEntity;
import com.eventty.userservice.domain.code.ErrorCode;

public class UserInfoNotFoundException extends UserException {
    private static final String[] fields = {"userId"};

    public UserInfoNotFoundException(Long id){
        super(ErrorCode.USER_INFO_NOT_FOUND, id, fields);
    }
}