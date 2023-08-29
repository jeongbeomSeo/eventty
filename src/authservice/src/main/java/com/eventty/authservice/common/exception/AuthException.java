package com.eventty.authservice.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import com.eventty.authservice.common.Enum.ErrorCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/*
로직 과정 중에 예외가 발생해서 예외를 던져주는 과정에서 보통 단 한번의 예외만으로 처리가 중단되는 것으로 알고 있습니다.
해당 클래스에서 List<ErrrorResponseDTO.FieldError> errors를 사용하려면 예외가 발생했을 때
매개변수로 건네 줘야 유효하고 아니면 빈 값으로 ControllerAdvice에 건네집니다.

저는 일단 필요성을 못 느껴서 뻈습니다.
 */

@Getter
@NoArgsConstructor
public class AuthException extends RuntimeException{

    private ErrorCode errorCode;
    protected AuthException(ErrorCode errorcode, String message) {
        super(message);
        this.errorCode = errorcode;
    }

    /*
    Customizing 예외는 메세지 작성하는 것을 기본으로 작업 진행해 주세요

    protected AuthException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
    */
}
