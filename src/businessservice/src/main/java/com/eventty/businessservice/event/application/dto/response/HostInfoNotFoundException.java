package com.eventty.businessservice.event.application.dto.response;

import com.eventty.businessservice.event.domain.Enum.ErrorCode;
import com.eventty.businessservice.event.domain.exception.BusinessException;

public class HostInfoNotFoundException extends BusinessException{
    public final static BusinessException EXCEPTION = new HostInfoNotFoundException();
    private HostInfoNotFoundException(){
        super(ErrorCode.HOST_INFO_NOT_FOUND);
    }
}
