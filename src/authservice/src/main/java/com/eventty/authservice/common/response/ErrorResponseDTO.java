package com.eventty.authservice.common.response;

import jakarta.validation.ConstraintViolation;
import org.springframework.validation.BindingResult;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.eventty.authservice.common.Enum.ErrorCode;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/** 실패 시 응답 객체 예시
 {
 "success": false,
 "code": "C004",
 "message": "invalid type value",
 "error" : []
 }
 */

@Getter
public class ErrorResponseDTO extends ResponseDTO{


    private List<FieldError> errors;
    private ErrorResponseDTO(ErrorCode errorcode) {
        super(false, errorcode.getCode(), errorcode.getMessage());
        this.errors = new ArrayList<>();
    }

    private ErrorResponseDTO(ErrorCode errorCode, List<FieldError> errors) {
        super(false, errorCode.getCode(), errorCode.getMessage());
        this.errors = errors;
    }

    /*
    정적 팩토리 메소드 of : 입력 매개변수에 따라 유연하게 ErrorResponse 객체를 반환하므로써 다양한 예외처리에 대응
     */

    public static ErrorResponseDTO of(final ErrorCode code) {
        return new ErrorResponseDTO(code);
    }

    public static ErrorResponseDTO of(final ErrorCode code, final List<FieldError> errors) {
        return new ErrorResponseDTO(code, errors);
    }

    public static ErrorResponseDTO of(final ErrorCode code, final BindingResult bindingResult) {
        return new ErrorResponseDTO(code, FieldError.of(bindingResult));
    }


    public static ErrorResponseDTO of(final ErrorCode code, final Set<ConstraintViolation<?>> constraintViolations) {
        return new ErrorResponseDTO(code, FieldError.of(constraintViolations));
    }

    /*
    public static ErrorResponseDTO of(MethodArgumentTypeMismatchException e) {
        final String value = e.getValue() == null ? "" : e.getValue().toString();
        final List<FieldError> errors = FieldError.of(e.getName(), value, e.getErrorCode());
        return new ErrorResponseDTO(ErrorCode.INVALID_TYPE_VALUE, errors);
    }
    */

    /*
    @Validated를 사용할 경우 해당 메서드는 불필요해 보입니다.

    public static ErrorResponseDTO of(final ErrorCode code, final String missingParameterName) {
        return new ErrorResponseDTO(code, FieldError.of(missingParameterName, "", "parameter must required"));
    }
    */

     /*
    오류 필드 정보를 담는 내부 클래스
     */

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class FieldError {
        private String field;
        private String value;
        private String reason;

        FieldError(String field, String value, String reason) {
            this.field = field;
            this.value = value;
            this.reason = reason;
        }

        static List<FieldError> of(final String field, final String value, final String reason) {
            List<FieldError> fieldErrors = new ArrayList<>();
            fieldErrors.add(new FieldError(field, value, reason));
            return fieldErrors;
        }

        // BindResult를 기반으로 오류 정보 생성 => @Valid (RequestBody, DTO, Entity,  ...)
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

        // ConstraintViolation을 기반으로 오류 정보 생성 => @Validated ( @Requestparams, @PathVariable, ...) and DB Entity Validation
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
