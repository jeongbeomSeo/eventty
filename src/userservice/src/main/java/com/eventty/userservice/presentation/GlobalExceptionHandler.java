package com.eventty.userservice.presentation;

import com.eventty.userservice.domain.code.ErrorCode;
import com.eventty.userservice.domain.exception.UserException;
import com.eventty.userservice.presentation.dto.ErrorResponseDTO;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.PropertyValueException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import static com.eventty.userservice.domain.code.ErrorCode.*;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 지원하지 않는 HTTP 요청 메서드에 대한 예외 처리
    @ExceptionHandler
    protected ResponseEntity<ErrorResponseDTO> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        final ErrorResponseDTO response = ErrorResponseDTO.of(METHOD_NOT_ALLOWED);
        return new ResponseEntity<>(response, HttpStatus.METHOD_NOT_ALLOWED);
    }

    // @Validated 에서 발생한 binding error 에 대한 예외 처리(DB관련)
    @ExceptionHandler
    protected ResponseEntity<ErrorResponseDTO> handleConstraintViolationException(ConstraintViolationException e) {
        final ErrorResponseDTO response = ErrorResponseDTO.of(INVALID_INPUT_VALUE, e.getConstraintViolations());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // @Valid 에서 조건이 맞지 않았을 경우(Rest API 파라미터 관련)
    @ExceptionHandler
    protected ResponseEntity<ErrorResponseDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        final ErrorResponseDTO response = ErrorResponseDTO.of(INVALID_INPUT_VALUE, e.getBindingResult());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // JSON을 파싱하다 문제가 발생한 경우(Request Body와 형식이 맞지 않는 경우, JSON 형태가 지켜지지 않았을 경우)
    @ExceptionHandler
    protected ResponseEntity<ErrorResponseDTO> handleHttpMessageNotReadableException(HttpMessageNotReadableException e){
        log.error("\ndetail message=>" + e.getMessage());
        final ErrorResponseDTO response = ErrorResponseDTO.of(INVALID_JSON);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // 유저 정보가 존재하지 않을 경우 예외처리
    @ExceptionHandler(UserException.class)
    public ResponseEntity<ErrorResponseDTO> handleUserInfoNotFoundException(UserException e) {
        ErrorCode errorCode = e.getErrorCode();
        return ResponseEntity
                .status(errorCode.getStatus())
                .body(ErrorResponseDTO.of(errorCode));
    }

    // 기타 예외처리
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponseDTO> handleException(Exception e) {
        log.error("Exception: ", e);
        final ErrorResponseDTO response = ErrorResponseDTO.of(INTERNAL_ERROR);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
