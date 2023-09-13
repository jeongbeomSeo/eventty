package com.eventty.businessservice.domains.event.domain.exception;

import com.eventty.businessservice.common.Enum.ErrorCode;
import com.eventty.businessservice.common.exception.BusinessException;

public class CategoryNotFoundException extends BusinessException{
    public final static BusinessException EXCEPTION = new CategoryNotFoundException();
    private CategoryNotFoundException(){
        super(ErrorCode.CATEGORY_NOT_FOUND);
    }
}
