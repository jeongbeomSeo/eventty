package com.eventty.businessservice.common.exception;

import com.eventty.businessservice.common.response.ErrorResponseDTO;
import com.eventty.businessservice.common.Enum.ErrorCode;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import static com.eventty.businessservice.common.Enum.ErrorCode.*;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 지원하지 않는 HTTP 요청 메서드에 대한 예외 처리
    @ExceptionHandler
    protected ResponseEntity<ErrorResponseDTO> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        final ErrorResponseDTO response = ErrorResponseDTO.of(METHOD_NOT_ALLOWED);
        return new ResponseEntity<>(response, HttpStatus.METHOD_NOT_ALLOWED);
    }

    // @Validated 에서 발생한 binding error 에 대한 예외 처리
    @ExceptionHandler
    protected ResponseEntity<ErrorResponseDTO> handleBindException(ConstraintViolationException e) {
        final ErrorResponseDTO response = ErrorResponseDTO.of(INVALID_INPUT_VALUE, e.getConstraintViolations());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // @Valid 에서 발생한 binding error 에 대한 예외 처리
    @ExceptionHandler
    protected ResponseEntity<ErrorResponseDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        final ErrorResponseDTO response = ErrorResponseDTO.of(INVALID_INPUT_VALUE, e.getBindingResult());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // binding error 에 대한 예외 처리 (주로 MVC 에서 @ModelAttribute 에서 발생)
    @ExceptionHandler
    protected ResponseEntity<ErrorResponseDTO> handleBindException(BindException e) {
        final ErrorResponseDTO response = ErrorResponseDTO.of(INVALID_INPUT_VALUE, e.getBindingResult());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // enum type 일치하지 않아서 발생하는 binding error 대한 예외 처리 (주로 @PathVariable 시 잘못된 type 데이터가 들어왔을 때 에러)
    @ExceptionHandler
    protected ResponseEntity<ErrorResponseDTO> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        final ErrorResponseDTO response = ErrorResponseDTO.of(e);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // 비즈니스 요구사항에 따른 예외처리
    @ExceptionHandler
    protected ResponseEntity<ErrorResponseDTO> handleBusinessException(BusinessException e) {
        final ErrorCode errorCode = e.getErrorCode();
        final ErrorResponseDTO response = ErrorResponseDTO.of(errorCode, e.getErrors());
        return new ResponseEntity<>(response, HttpStatus.valueOf(errorCode.getStatus()));
    }

    // 기타 예외처리
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponseDTO> handleException(Exception e) {
        log.error("Exception: ", e);
        final ErrorResponseDTO response = ErrorResponseDTO.of(INTERNAL_ERROR);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}