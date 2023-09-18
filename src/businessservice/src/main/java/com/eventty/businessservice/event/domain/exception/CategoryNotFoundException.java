package com.eventty.businessservice.event.domain.exception;

import com.eventty.businessservice.event.domain.Enum.ErrorCode;

public class CategoryNotFoundException extends BusinessException{
    public final static BusinessException EXCEPTION = new CategoryNotFoundException();
    private CategoryNotFoundException(){
        super(ErrorCode.CATEGORY_NOT_FOUND);
    }
}
