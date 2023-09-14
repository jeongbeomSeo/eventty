package com.eventty.authservice.api.exception;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;

import java.net.URI;

import lombok.Getter;

@Getter
public class ApiException extends RuntimeException {

    private String message;
    private HttpStatusCode HttpStatusCode;
    public ApiException(URI uri, HttpMethod httpMethod, HttpStatusCode HttpStatusCode) {
        this.message = "원격 API 요청에 실패했습니다. \n" +
                "원인 API URI:" + uri + ", Method: " + httpMethod;
        this.HttpStatusCode = HttpStatusCode;
    }

}
