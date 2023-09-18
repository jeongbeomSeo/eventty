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
public class ErrorResponseDTO {

    private String code;

    private ErrorResponseDTO(ErrorCode errorcode) {
        this.code = errorcode.getCode();
    }

    /*
    정적 팩토리 메소드 of : 입력 매개변수에 따라 유연하게 ErrorResponse 객체를 반환하므로써 다양한 예외처리에 대응
     */

    public static ErrorResponseDTO of(final ErrorCode errorcode) {
        return new ErrorResponseDTO(errorcode);
    }
}