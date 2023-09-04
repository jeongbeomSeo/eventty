package com.eventty.userservice.presentation;

import com.eventty.userservice.domain.code.ErrorCode;
import com.eventty.userservice.domain.exception.UserException;
import com.eventty.userservice.presentation.dto.ErrorResponseDTO;
import com.eventty.userservice.presentation.dto.ResponseDTO;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import static com.eventty.userservice.domain.code.ErrorCode.*;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 지원하지 않는 HTTP 요청 메서드에 대한 예외 처리
    @ExceptionHandler
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    protected ResponseDTO handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        return ErrorResponseDTO.of(METHOD_NOT_ALLOWED);
    }

    // @Validated 에서 발생한 binding error 에 대한 예외 처리(RequestParams, PathVariable의 유효성 검증 실패 및 DB관련)
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseDTO handleConstraintViolationException(ConstraintViolationException e) {
        return ErrorResponseDTO.of(INVALID_INPUT_VALUE, e.getConstraintViolations());
    }

    // @Valid 에서 조건이 맞지 않았을 경우 (DTO, Entity 유효성 검증)
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseDTO handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return ErrorResponseDTO.of(INVALID_INPUT_VALUE, e.getBindingResult());
    }

    // JSON을 파싱하다 문제가 발생한 경우(@Valid @Request Body와 형식이 맞지 않는 경우, JSON 형태가 지켜지지 않았을 경우)
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseDTO handleHttpMessageNotReadableException(HttpMessageNotReadableException e){
        log.error("\ndetail message=>" + e.getMessage());
        return ErrorResponseDTO.of(INVALID_JSON);
    }

    // 유저 정보가 존재하지 않을 경우 예외처리
    @ExceptionHandler(UserException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDTO handleUserInfoNotFoundException(UserException e) {
        return ErrorResponseDTO.of(e.getErrorCode());
    }

    // 기타 예외처리
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ResponseDTO handleException(Exception e) {
        log.error("Exception: ", e);
        return ErrorResponseDTO.of(INTERNAL_ERROR);
    }
}
