package com.eventty.applyservice.presentation;

import com.eventty.applyservice.presentation.dto.ErrorResponseDTO;
import com.eventty.applyservice.presentation.dto.ResponseDTO;
import com.eventty.applyservice.presentation.dto.SuccessResponseDTO;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice(basePackages = "com.eventty.applyservice")
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