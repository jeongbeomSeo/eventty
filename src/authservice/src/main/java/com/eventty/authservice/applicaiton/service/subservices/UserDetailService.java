package com.eventty.authservice.applicaiton.service.subservices;

import com.eventty.authservice.applicaiton.service.utils.CustomPasswordEncoder;
import com.eventty.authservice.domain.Enum.UserRole;
import com.eventty.authservice.domain.entity.AuthUserEntity;
import com.eventty.authservice.presentation.dto.request.PWChangeRequestDTO;

import java.util.List;

public interface UserDetailService {

    Long delete(AuthUserEntity authUserEntity);
    Long create(AuthUserEntity authUserEntity, UserRole userRole);

    void validateEmail(String email);
    void validationUser(AuthUserEntity authUserEntity);

    AuthUserEntity findAuthUser(String email);

    AuthUserEntity findAuthUser(Long userId);
    AuthUserEntity changePwAuthUser(String encryptedPassword, AuthUserEntity authUserEntity);
    List<AuthUserEntity> findNotDeletedAuthUserList(List<Long> userIds);
}
