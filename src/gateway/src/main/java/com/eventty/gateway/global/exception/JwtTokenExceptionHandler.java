package com.eventty.gateway.global.exception;

import com.eventty.gateway.global.dto.ErrorResponseDTO;
import com.eventty.gateway.global.dto.ResponseDTO;
import com.eventty.gateway.global.exception.utils.DataErrorLogger;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Order(-1)
@RequiredArgsConstructor
public class JwtTokenExceptionHandler implements ErrorWebExceptionHandler {
    private final ObjectMapper objectMapper;
    private final DataErrorLogger dataErrorLogger;
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {

        ServerHttpResponse response = exchange.getResponse();

        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        ErrorResponseDTO errorResponseDTO;
        if (ex instanceof JwtException) {
            log.error("Error Message: {}", ((JwtException) ex).getErrorCode().getMessage());
            dataErrorLogger.logging((JwtException) ex);

            response.setStatusCode(HttpStatusCode.valueOf(((JwtException) ex).getErrorCode().getStatus()));

            errorResponseDTO = ErrorResponseDTO.of(((JwtException) ex).getErrorCode());
        }
        else if (ex instanceof JsonProcessingException) {
            log.error("Error Message: {}", ex.getMessage());
            response.setStatusCode(HttpStatusCode.valueOf(ErrorCode.BAD_CREDENTIALS.getStatus()));

            errorResponseDTO = ErrorResponseDTO.of(ErrorCode.BAD_CREDENTIALS);
        }
        else if (ex instanceof IOException){
            log.error("Error Message: {}", ErrorCode.INTERNAL_ERROR);
            response.setStatusCode(HttpStatusCode.valueOf(ErrorCode.INTERNAL_ERROR.getStatus()));

            errorResponseDTO = ErrorResponseDTO.of(ErrorCode.INTERNAL_ERROR);
        }
        else {
            log.error("Error Meesage: {}", ex.getMessage());
            response.setStatusCode(HttpStatusCode.valueOf(ErrorCode.FAIL_AUTHENTICATION.getStatus()));

            errorResponseDTO = ErrorResponseDTO.of(ErrorCode.FAIL_AUTHENTICATION);
        }

        byte[] bytes;
        try {
            bytes = objectMapper.writeValueAsBytes(ResponseDTO.of(errorResponseDTO));
        } catch (JsonProcessingException e) {
            log.error("JSON 변환 오류", e);
            bytes = "{\"error\":\"Internal Server Error\"}".getBytes(StandardCharsets.UTF_8);
        }
        DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
        return exchange.getResponse().writeWith(Flux.just(buffer));
    }
}
