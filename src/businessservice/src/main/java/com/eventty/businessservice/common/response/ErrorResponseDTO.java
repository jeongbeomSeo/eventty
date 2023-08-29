package com.eventty.businessservice.common.response;

import com.eventty.businessservice.common.Enum.ErrorCode;
import jakarta.validation.ConstraintViolation;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

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

    public static ErrorResponseDTO of(final ErrorCode code, final List<FieldError> errors) {
        return new ErrorResponseDTO(code, errors);
    }

    public static ErrorResponseDTO of(final ErrorCode code, final BindingResult bindingResult) {
        return new ErrorResponseDTO(code, FieldError.of(bindingResult));
    }

    public static ErrorResponseDTO of(MethodArgumentTypeMismatchException e) {
        // MethodArgumentTypeMismatchException에서 정보 추출하여 ErrorResponseDTO 생성
        final String value = e.getValue() == null ? "" : e.getValue().toString();
        final List<FieldError> errors = FieldError.of(e.getName(), value, e.getErrorCode());
        return new ErrorResponseDTO(ErrorCode.INVALID_TYPE_VALUE, errors);
    }

    public static ErrorResponseDTO of(final ErrorCode code, final Set<ConstraintViolation<?>> constraintViolations) {
        return new ErrorResponseDTO(code, FieldError.of(constraintViolations));
    }

    public static ErrorResponseDTO of(final ErrorCode code, final String missingParameterName) {
        return new ErrorResponseDTO(code, FieldError.of(missingParameterName, "", "parameter must required"));
    }


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