package com.eventty.userservice.presentation.dto;

import com.eventty.userservice.domain.code.ErrorCode;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import jakarta.validation.ConstraintViolation;
import lombok.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.*;
import java.util.stream.Collectors;

@Setter
@Getter
public class ErrorResponseDTO extends ResponseDTO {

    private final List<FieldError> errors;

    private ErrorResponseDTO(ErrorCode errorCode, List<FieldError> errors) {
        super(false, errorCode.getCode(), errorCode.getMessage());
        this.errors = errors;
    }

    private ErrorResponseDTO(ErrorCode errorCode) {
        super(false, errorCode.getCode(), errorCode.getMessage());
        this.errors = new ArrayList<>();
    }

    /*
    정적 팩토리 메소드 of : 입력 매개변수에 따라 유연하게 ErrorResponse 객체를 반환하므로써 다양한 예외처리에 대응
     */

    public static ErrorResponseDTO of(final ErrorCode code) {
        return new ErrorResponseDTO(code);
    }

    public static ErrorResponseDTO of(final ErrorCode code, final BindingResult bindingResult) {
        return new ErrorResponseDTO(code, FieldError.of(bindingResult));
    }

    public static ErrorResponseDTO of(final ErrorCode code, final Set<ConstraintViolation<?>> constraintViolations) {
        return new ErrorResponseDTO(code, FieldError.of(constraintViolations));
    }

    /*
    오류 필드 정보를 담는 내부 클래스
     */
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    static class FieldError {
        private String field;
        private String value;
        private String reason;

        FieldError(String field, String value, String reason) {
            this.field = field;
            this.value = value;
            this.reason = reason;
        }

        // BindingResult를 기반으로 오류 정보 생성
        static List<FieldError> of(final BindingResult bindingResult) {
            final List<org.springframework.validation.FieldError> fieldErrors = bindingResult.getFieldErrors();
            return fieldErrors.stream()
                    .map(error -> new FieldError(
                            error.getField(),
                            error.getRejectedValue() == null ? "" : error.getRejectedValue().toString(),
                            error.getDefaultMessage()
                    ))
                    .collect(Collectors.toList());
        }

        // 데이터 유효성 검사 실패 시 발생하는 예외에서 실패 정보를 담고 있는 ConstraintViolation을 기반으로 오류 정보 생성
        static List<FieldError> of(final Set<ConstraintViolation<?>> constraintViolations) {
            return constraintViolations.stream()
                    .map(error -> new FieldError(
                            error.getPropertyPath().toString(),
                            error.getInvalidValue().toString(),
                            error.getMessage()
                    ))
                    .collect(Collectors.toList());
        }
    }
}
