package com.eventty.authservice.applicaiton.service.Facade;

import com.eventty.authservice.applicaiton.dto.TokenDTO;
import com.eventty.authservice.applicaiton.service.subservices.AuthService;
import com.eventty.authservice.applicaiton.service.subservices.AuthServiceImpl;
import com.eventty.authservice.presentation.dto.UserLoginRequestDTO;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.eventty.authservice.api.ApiClient;
import com.eventty.authservice.api.dto.UserCreateRequestDTO;
import com.eventty.authservice.applicaiton.service.subservices.UserDetailService;
import com.eventty.authservice.applicaiton.service.subservices.UserDetailServiceImpl;
import com.eventty.authservice.domain.Enum.UserRole;
import com.eventty.authservice.domain.entity.AuthUserEntity;
import com.eventty.authservice.presentation.dto.FullUserCreateRequestDTO;
import com.eventty.authservice.applicaiton.service.utils.CustomConverter;
import com.eventty.authservice.applicaiton.service.utils.CustomPasswordEncoder;

@Service
public class UserServiceImpl implements UserService {

    private final UserDetailService userDetailService;
    private final AuthService authService;
    private final ApiClient apiClient;
    private final CustomConverter customConverter;
    private final CustomPasswordEncoder customPasswordEncoder;


    @Autowired
    public UserServiceImpl(UserDetailServiceImpl userServiceImpl,
                           AuthServiceImpl authServiceImpl,
                           @Lazy ApiClient apiClient,
                           @Lazy CustomConverter converterService,
                           @Lazy CustomPasswordEncoder customPasswordEncoder) {
        this.userDetailService = userServiceImpl;
        this.authService = authServiceImpl;
        this.apiClient = apiClient;
        this.customConverter = converterService;
        this.customPasswordEncoder = customPasswordEncoder;
    }

    @Override
    public TokenDTO login(UserLoginRequestDTO userLoginRequestDTO) {

        AuthUserEntity authUserEntity = userDetailService.findAuthUser(userLoginRequestDTO.getEmail());

        authService.match(userLoginRequestDTO, authUserEntity, customPasswordEncoder);

        TokenDTO token = authService.getToken(authUserEntity);

        return token;
    }

    @Override
    @Transactional
    public Long createUser(FullUserCreateRequestDTO fullUserCreateRequestDTO, UserRole role) {

        AuthUserEntity authUserEntity = customConverter.userDTOToAuthEntityConvert(fullUserCreateRequestDTO, customPasswordEncoder);

        Long userId = userDetailService.create(authUserEntity, role);

        // API 요청 로직
        UserCreateRequestDTO userCreateRequestDTO = customConverter.fullUserDTOToUserDTOConvert(fullUserCreateRequestDTO, userId);
        apiClient.createUserApi(userCreateRequestDTO);

        return userId;
    }

    @Override
    public void validateEmailNotDuplicated(String email) {
        userDetailService.validateEmail(email);
    }
}
