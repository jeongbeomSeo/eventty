package com.eventty.authservice.applicaiton.service.Facade;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.eventty.authservice.api.ApiClient;
import com.eventty.authservice.api.dto.UserCreateRequestDTO;
import com.eventty.authservice.applicaiton.service.UserService;
import com.eventty.authservice.applicaiton.service.UserServiceImpl;
import com.eventty.authservice.domain.Enum.UserRole;
import com.eventty.authservice.domain.entity.AuthUserEntity;
import com.eventty.authservice.presentation.dto.FullUserCreateRequestDTO;

@Service
public class AuthServiceImpl implements AuthService{

    private final UserService userService;
    private final ApiClient apiClient;

    @Autowired
    public AuthServiceImpl(UserServiceImpl userServiceImpl,
                           @Lazy ApiClient apiClient) {
        this.userService = userServiceImpl;
        this.apiClient = apiClient;
    }


    @Override
    @Transactional
    public Long createUser(FullUserCreateRequestDTO fullUserCreateRequestDTO, UserRole role) {

        AuthUserEntity user = userService.create(fullUserCreateRequestDTO, role);

        Long authId = user.getId();

        // API 요청 로직
        UserCreateRequestDTO userCreateRequestDTO = fullUserCreateRequestDTO.toUserCreateRequestDTO(authId);
        apiClient.createUserApi(userCreateRequestDTO);

        // 강제로 Exception 발생 시켜서 Transactional 검증 => 성공
        // throw new RuntimeException("강제 Error 발생 시키기 ");

        return authId;
    }

    @Override
    public void validateEmailNotDuplicated(String email) {
        userService.emailValidationCheck(email);
    }
}
