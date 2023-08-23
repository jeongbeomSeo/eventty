package com.eventty.userservice.presentation;

import com.eventty.userservice.domain.code.ErrorCode;
import com.eventty.userservice.domain.exception.UserException;
import com.eventty.userservice.presentation.dto.ErrorResponseDTO;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.PropertyValueException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @ExceptionHandler(PropertyValueException.class)
    protected ResponseEntity<ErrorResponseDTO> handler(PropertyValueException e){
        log.error("Entity : " + e.getEntityName() + ", column : " + e.getPropertyName());
        final ErrorResponseDTO response = ErrorResponseDTO.of(NOTNULL_PROPERTY_ERROR);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
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

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ErrorResponseDTO> handleUserNotFoundException(UserException e) {
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
