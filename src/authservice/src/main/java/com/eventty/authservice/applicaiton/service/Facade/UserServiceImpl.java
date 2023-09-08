package com.eventty.authservice.applicaiton.service.Facade;

import com.eventty.authservice.applicaiton.dto.TokensDTO;
import com.eventty.authservice.applicaiton.service.subservices.AuthService;
import com.eventty.authservice.applicaiton.service.subservices.AuthServiceImpl;
import com.eventty.authservice.presentation.dto.request.GetNewTokensRequestDTO;
import com.eventty.authservice.presentation.dto.request.UserLoginRequestDTO;
import com.eventty.authservice.presentation.dto.response.NewTokensResponseDTO;
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
import com.eventty.authservice.presentation.dto.request.FullUserCreateRequestDTO;
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
    public TokensDTO login(UserLoginRequestDTO userLoginRequestDTO) {

        AuthUserEntity authUserEntity = userDetailService.findAuthUser(userLoginRequestDTO.getEmail());

        authService.credentialMatch(userLoginRequestDTO, authUserEntity, customPasswordEncoder);

        TokensDTO token = authService.getToken(authUserEntity);

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

    @Override
    public NewTokensResponseDTO getNewTokens(GetNewTokensRequestDTO getNewTokensRequestDTO) {

        // Auth Service를 이용해서 accessToken과 RefreshToken을 받아오기. 내부 로직에서 ResponseDTO로 Mapping하는 과정 필요
        AuthUserEntity authUserEntity = userDetailService.findAuthUser(getNewTokensRequestDTO.getUserId());

        TokensDTO newTokens = authService.getNewTokens(authUserEntity, getNewTokensRequestDTO);

        // 추가적으로 ResponseDTO에 담을 필요한 정보가 추가 될 수 있음.

        return customConverter.TokensDTOToNewTokensResponseDTO(newTokens);
    }
}
