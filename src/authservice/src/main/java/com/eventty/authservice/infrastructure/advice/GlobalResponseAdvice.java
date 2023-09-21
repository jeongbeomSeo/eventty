package com.eventty.authservice.infrastructure.advice;

import com.eventty.authservice.global.Enum.ErrorCode;
import com.eventty.authservice.global.Enum.SuccessCode;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.eventty.authservice.global.response.ErrorResponseDTO;
import com.eventty.authservice.global.response.ResponseDTO;
import com.eventty.authservice.global.response.SuccessResponseDTO;

@RestControllerAdvice(basePackages = "com.eventty")
public class GlobalResponseAdvice implements ResponseBodyAdvice {

    // ResponseDTO로 오는 경우 외에 전부 실행
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return !ResponseDTO.class.isAssignableFrom(returnType.getParameterType());
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {

        if (body instanceof SuccessResponseDTO<?>) {
            SuccessResponseDTO successResponseDTO = (SuccessResponseDTO) body;
            return ResponseDTO.of(successResponseDTO);
        }

        if (body instanceof ErrorResponseDTO) {
            ErrorResponseDTO errorResponseDTO = (ErrorResponseDTO) body;
            return ResponseDTO.of(errorResponseDTO);
        }
        if (body instanceof Boolean) {
            Boolean isSuccess = (Boolean) body;
            return ResponseDTO.of(isSuccess);
        }

        return ResponseDTO.of(true);
    }
}

