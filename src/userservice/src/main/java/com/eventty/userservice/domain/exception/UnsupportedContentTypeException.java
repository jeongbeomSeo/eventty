package com.eventty.userservice.domain.exception;

import com.eventty.userservice.domain.code.ErrorCode;

public class UnsupportedContentTypeException extends UserException{
    private static final String[] fields = {"contentType"};

    public UnsupportedContentTypeException(Object causedErrorData) {
        super(ErrorCode.CONTENTTYPE_ERROR, causedErrorData, fields);
    }
}
