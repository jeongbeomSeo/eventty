package com.eventty.authservice.applicaiton.service.Facade;

import com.eventty.authservice.domain.Enum.UserRole;
import com.eventty.authservice.presentation.dto.FullUserCreateRequestDTO;

public interface AuthService {

    Long createUser(FullUserCreateRequestDTO fullUserCreateRequestDTO, UserRole userRole);

    void validateEmailNotDuplicated(String email);
}
