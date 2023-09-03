package com.eventty.authservice.applicaiton.service.Facade;

import com.eventty.authservice.api.ApiClient;
import com.eventty.authservice.applicaiton.service.UserService;
import com.eventty.authservice.domain.Enum.Roles;
import com.eventty.authservice.presentation.dto.FullUserCreateRequestDTO;

public interface AuthService {

    void createUser(FullUserCreateRequestDTO fullUserCreateRequestDTO, Roles role);

    void isEmailDuplicate(String email);



}
