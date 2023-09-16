package com.eventty.authservice.applicaiton.service.Facade;

import com.eventty.authservice.applicaiton.dto.LoginSuccessDTO;
import com.eventty.authservice.applicaiton.dto.TokensDTO;
import com.eventty.authservice.applicaiton.service.subservices.AuthService;
import com.eventty.authservice.applicaiton.service.subservices.AuthServiceImpl;
import com.eventty.authservice.global.response.ResponseDTO;
import com.eventty.authservice.presentation.dto.request.GetNewTokensRequestDTO;
import com.eventty.authservice.presentation.dto.request.UserLoginRequestDTO;
import com.eventty.authservice.presentation.dto.response.LoginResponseDTO;
import com.eventty.authservice.presentation.dto.response.NewTokensResponseDTO;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
    public LoginSuccessDTO login(UserLoginRequestDTO userLoginRequestDTO) {

        // email을 이용해서 user 조회
        AuthUserEntity authUserEntity = userDetailService.findAuthUser(userLoginRequestDTO.getEmail());

        // 유저 삭제되어 있는지 확인
        userDetailService.validationUser(authUserEntity);

        // 비밀번호 매칭
        authService.credentialMatch(userLoginRequestDTO, authUserEntity, customPasswordEncoder);

        // 모든 과정 성공시 JWT, Refresh Token과 email, 권한을 각각 DTO에 담아서 LoginSuccessDTO에 담아서 반환 => 권한 X 역할만 담기
        LoginSuccessDTO loginSuccessDTO = customConverter.authUserEntityTologinSuccessDTO(authService, authUserEntity);

        return loginSuccessDTO;
    }

    @Override
    @Transactional
    public Long createUser(FullUserCreateRequestDTO fullUserCreateRequestDTO, UserRole role) {

        // 전달 받은 DTO로 Entity로 변환
        AuthUserEntity authUserEntity = customConverter.userDTOToAuthEntityConvert(fullUserCreateRequestDTO, customPasswordEncoder);

        // 유저 생성
        Long userId = userDetailService.create(authUserEntity, role);

        // API 요청 로직
        UserCreateRequestDTO userCreateRequestDTO = customConverter.fullUserDTOToUserDTO(fullUserCreateRequestDTO, userId);
        apiClient.createUserApi(userCreateRequestDTO);

        return userId;
    }

    @Override
    public Long deleteUser(Long userId) {
        // User Id로 유저 조회
        AuthUserEntity authUserEntity = userDetailService.findAuthUser(userId);

        // 유저 삭제
        return userDetailService.delete(authUserEntity);
    }

    @Override
    public void validateEmailNotDuplicated(String email) {
        userDetailService.validateEmail(email);
    }

    @Override
    public NewTokensResponseDTO getNewTokens(GetNewTokensRequestDTO getNewTokensRequestDTO) {

        // User Id를 이용해서 유저 조회
        AuthUserEntity authUserEntity = userDetailService.findAuthUser(getNewTokensRequestDTO.getUserId());

        // JWT와 RefreshToken 받아오기
        TokensDTO newTokens = authService.getNewTokens(authUserEntity, getNewTokensRequestDTO);

        return customConverter.tokensDTOToNewTokensResponseDTO(newTokens);
    }
}
