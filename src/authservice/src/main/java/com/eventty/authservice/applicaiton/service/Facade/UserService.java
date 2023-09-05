package com.eventty.authservice.applicaiton.service.Facade;

import com.eventty.authservice.applicaiton.dto.TokenDTO;
import com.eventty.authservice.domain.Enum.UserRole;
import com.eventty.authservice.presentation.dto.FullUserCreateRequestDTO;
import com.eventty.authservice.presentation.dto.UserLoginRequestDTO;

public interface UserService {

    Long createUser(FullUserCreateRequestDTO fullUserCreateRequestDTO, UserRole userRole);

    void validateEmailNotDuplicated(String email);

    TokenDTO login(UserLoginRequestDTO userLoginRequestDTO);
}
