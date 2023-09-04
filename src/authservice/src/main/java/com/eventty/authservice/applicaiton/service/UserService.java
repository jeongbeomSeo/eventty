package com.eventty.authservice.applicaiton.service;

import com.eventty.authservice.domain.Enum.UserRole;
import com.eventty.authservice.domain.entity.AuthUserEntity;
import com.eventty.authservice.presentation.dto.FullUserCreateRequestDTO;

public interface UserService {

    AuthUserEntity create(FullUserCreateRequestDTO fullUserCreateRequestDTO, UserRole userRole);

    boolean emailValidationCheck(String email);

}
