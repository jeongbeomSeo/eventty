package com.eventty.businessservice.domains.event.domain.exception;

import com.eventty.businessservice.common.Enum.ErrorCode;
import com.eventty.businessservice.common.exception.BusinessException;

public class TicketNotFoundException extends BusinessException {
    public final static BusinessException EXCEPTION = new TicketNotFoundException();
    private TicketNotFoundException(){
        super(ErrorCode.TICKET_NOT_FOUND);
    }
}
