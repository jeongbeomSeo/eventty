package com.eventty.authservice.api.exception;

import com.eventty.authservice.common.response.ErrorResponseDTO;
import lombok.Getter;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;

import java.net.URI;

@Getter
public class ApiException extends RuntimeException {

    private String message;
    private HttpStatusCode HttpStatusCode;
    public ApiException(URI uri, HttpMethod httpMethod, HttpStatusCode HttpStatusCode) {
        this.message = "원격 API 호출에 실패했습니다. \n원인 API URI:" + uri + ", Method: " + httpMethod;
        this.HttpStatusCode = HttpStatusCode;
    }

}
