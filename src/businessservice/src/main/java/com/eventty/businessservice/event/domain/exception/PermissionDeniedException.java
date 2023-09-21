package com.eventty.businessservice.event.domain.exception;

import com.eventty.businessservice.event.domain.Enum.ErrorCode;

public class PermissionDeniedException extends BusinessException {
    public PermissionDeniedException() {
        super(ErrorCode.PERMISSION_DENIED);
    }
}
