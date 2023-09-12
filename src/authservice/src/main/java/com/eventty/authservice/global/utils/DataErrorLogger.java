package com.eventty.authservice.global.utils;

import com.eventty.authservice.domain.entity.AuthUserEntity;
import com.eventty.authservice.domain.exception.*;
import com.eventty.authservice.global.exception.AuthException;
import com.eventty.authservice.presentation.dto.request.GetNewTokensRequestDTO;
import com.eventty.authservice.presentation.dto.request.UserLoginRequestDTO;
import jakarta.validation.ConstraintViolation;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.Set;

@Slf4j
@Service
public class DataErrorLogger {

    // BindingResult를 기반으로 오류 정보 출력
     public void logging(final BindingResult bindingResult) {
         StringBuffer sb = new StringBuffer();
         sb.append("Binding Result Details: \n");
         bindingResult.getFieldErrors()
                 .forEach(error -> {
                     sb.append("Field(").append(error.getField()).append("): ");
                     sb.append(error.getRejectedValue() == null ? "" : error.getRejectedValue().toString());
                     sb.append(" // Meesage: ");
                     sb.append(error.getDefaultMessage());
                     sb.append("\n");
                     }
            );
         log.error(sb.toString());
     }

    // 데이터베이스 제약 조건이 위반될 때 발생 (Entity 필드 유효성 검사나 데이터베이스 테이블의 unique 제약 조건 등이 위반될 경우 발생)
    // ConstraintViolation을 기반으로 오류 정보 출력  => @Validated ( @Requestparams, @PathVariable, ...) and DB Entity Validation
    public void logging(final Set<ConstraintViolation<?>> constraintViolations) {
        StringBuffer sb = new StringBuffer();
        sb.append("ConstraintViolation Result Details: \n");
        constraintViolations
                .forEach(error -> {
                    sb.append(error.getPropertyPath().toString()).append(": ");
                    sb.append(error.getInvalidValue().toString());
                    sb.append(" and Meesage: ");
                    sb.append(error.getMessage());
                    sb.append("\n");
                }
            );
        log.error(sb.toString());
    }

    public void loggingAuth(AuthException e) {
        Object causedErrorData = e.getCausedErrorData();
        log.error("Input Data Details\n");

         if (e instanceof UserNotFoundException) {
             if (causedErrorData instanceof String email) {
                 log.error("Email: {}", email);
             }
             if (causedErrorData instanceof Long userId) {
                 log.error("User Id: {}", userId);
             }
         }
         if (e instanceof AccessDeletedUserException) {
             if (causedErrorData instanceof AuthUserEntity authUserEntity) {
                 log.error("User Id: {}, Email: {}", authUserEntity.getId(), authUserEntity.getEmail());
             }
         }
         if (e instanceof DuplicateEmailException) {
             if (causedErrorData instanceof String email) {
                 log.error("Eamil: {}", email);
             }
         }
         if (e instanceof InvalidPasswordException) {
             if (causedErrorData instanceof UserLoginRequestDTO userLoginRequestDTO) {
                 log.error("Email: {}, password: {}", userLoginRequestDTO.getEmail(), userLoginRequestDTO.getPassword());
             }
         }
         if (e instanceof InValidRefreshTokenException) {
             if (causedErrorData instanceof GetNewTokensRequestDTO getNewTokensRequestDTO) {
                 log.error("User Id: {}, Refresh Token: {}", getNewTokensRequestDTO.getUserId(), getNewTokensRequestDTO.getRefreshToken());
             }
         }
         if (e instanceof RefreshTokenNotFoundException) {
             if (causedErrorData instanceof Long userId) {
                 log.error("User Id: {}", userId);
             }
         }
    }

}
