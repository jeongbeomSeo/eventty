package com.eventty.businessservice.domain.exception;

import com.eventty.businessservice.common.exception.BusinessException;
import com.eventty.businessservice.common.Enum.ErrorCode;

public class EventNotFoundException extends BusinessException {
    public final static BusinessException EXCEPTION = new EventNotFoundException();
    private EventNotFoundException(){
        super(ErrorCode.EVENT_NOT_FOUND);
    }
}
