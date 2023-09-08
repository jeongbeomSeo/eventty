package com.eventty.authservice.applicaiton.service.subservices;

import com.eventty.authservice.domain.Enum.UserRole;
import com.eventty.authservice.domain.entity.AuthUserEntity;

public interface UserDetailService {

    Long create(AuthUserEntity authUserEntity, UserRole userRole);

    void validateEmail(String email);

    AuthUserEntity findAuthUser(String email);

    AuthUserEntity findAuthUser(Long userId);
}
