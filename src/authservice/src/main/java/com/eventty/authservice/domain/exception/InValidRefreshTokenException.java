package com.eventty.authservice.domain.exception;

import com.eventty.authservice.global.Enum.ErrorCode;
import com.eventty.authservice.global.exception.AuthException;
import com.eventty.authservice.presentation.dto.request.GetNewTokensRequestDTO;

public class InValidRefreshTokenException extends AuthException {
    private static final String[] fields = {"userId", "refreshToken"};
    public InValidRefreshTokenException(GetNewTokensRequestDTO getNewTokensRequestDTO) {
        super(ErrorCode.INVALID_REFRESH_TOKEN, getNewTokensRequestDTO, fields);
    }
}