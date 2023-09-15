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

    // 모든 Response에 적용
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {

        if (body instanceof SuccessResponseDTO<?>) {
            SuccessResponseDTO successResponseDTO = (SuccessResponseDTO) body;
            ResponseDTO responseDTO = ResponseDTO.of(successResponseDTO);
            return responseDTO;
        }

        if (body == null){
            return new ResponseDTO();
        }

        if (body instanceof ErrorResponseDTO) {
            ErrorResponseDTO errorResponseDTO = (ErrorResponseDTO) body;
            ResponseDTO responseDTO = ResponseDTO.of(errorResponseDTO);
            return responseDTO;
        }

        return null;
    }
}