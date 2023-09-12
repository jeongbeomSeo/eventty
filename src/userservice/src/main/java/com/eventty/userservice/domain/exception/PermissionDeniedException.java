package com.eventty.userservice.domain.exception;

import com.eventty.userservice.domain.code.ErrorCode;

public class PermissionDeniedException extends UserException {
    public final static PermissionDeniedException EXCEPTION = new PermissionDeniedException();
    private PermissionDeniedException() {
        super(ErrorCode.PERMISSION_DENIED);
    }
}
