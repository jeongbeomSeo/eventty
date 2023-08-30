package com.eventty.authservice.api.exception;

import com.eventty.authservice.common.Enum.ErrorCode;

import com.eventty.authservice.common.response.ErrorResponseDTO;
import lombok.Getter;
import org.springframework.http.HttpStatusCode;

@Getter
public class ApiException extends RuntimeException {

    private ErrorResponseDTO errorResponseDTO;
    private HttpStatusCode httpStatusCode;
    public ApiException(ErrorResponseDTO errorResponseDTO, HttpStatusCode httpStatusCode) {
        this.errorResponseDTO = errorResponseDTO;
        this.httpStatusCode = httpStatusCode;
    }
}
