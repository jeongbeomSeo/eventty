package com.eventty.businessservice.domains.event.domain.exception;

import com.eventty.businessservice.common.ErrorCode;
import com.eventty.businessservice.common.exception.BusinessException;


public class EventDetailNotFoundException extends BusinessException {
    public final static BusinessException EXCEPTION = new EventDetailNotFoundException();
    private EventDetailNotFoundException(){
        super(ErrorCode.EVENT_DETAIL_NOT_FOUND);
    }
}