package com.eventty.authservice.applicaiton.service.Facade;

import com.eventty.authservice.applicaiton.dto.CsrfTokenDTO;
import com.eventty.authservice.applicaiton.dto.LoginSuccessDTO;
import com.eventty.authservice.applicaiton.dto.TokenParsingDTO;
import com.eventty.authservice.applicaiton.dto.TokensDTO;
import com.eventty.authservice.applicaiton.service.subservices.AuthService;
import com.eventty.authservice.applicaiton.service.subservices.AuthServiceImpl;
import com.eventty.authservice.domain.entity.AuthorityEntity;
import com.eventty.authservice.presentation.dto.request.AuthenticationUserRequestDTO;
import com.eventty.authservice.presentation.dto.request.GetNewTokensRequestDTO;
import com.eventty.authservice.presentation.dto.request.UserLoginRequestDTO;
import com.eventty.authservice.presentation.dto.response.AuthenticationDetailsResponseDTO;
import com.eventty.authservice.presentation.dto.response.NewTokensResponseDTO;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.parameters.P;
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
    public AuthenticationDetailsResponseDTO authenticateUser(AuthenticationUserRequestDTO authenticationUserRequestDTO) {

        // 1차 검증을 통해 userId와 Token Update 필요한지 정보 가져오기
        TokenParsingDTO tokenParsingDTO = authService.getTokenParsingDTO(authenticationUserRequestDTO);

        // 2차 검증 (삭제되어 있는 User인지 확인)
        AuthUserEntity authUserEntity = userDetailService.findAuthUser(tokenParsingDTO.userId());

        // 3차 검증 (CSRF 검증) => 성공시 재발급 및 저장
        CsrfTokenDTO csrfTokenDTO = new CsrfTokenDTO(authUserEntity.getId(), authenticationUserRequestDTO.csrfToken());
        authService.csrfTokenValidationCheck(csrfTokenDTO);
        String newCsrfToken = authService.getNewCsrfToken(csrfTokenDTO);

        // 모든 검증을 마친 후 토큰 업데이트가 필요하면 수행
        TokensDTO tokensDTO = new TokensDTO(authenticationUserRequestDTO.accessToken(), authenticationUserRequestDTO.refreshToken());

        if (tokenParsingDTO.needsUpdate()) {
            // 검증 로직 없이 새로운 토큰 가져오기
            tokensDTO = authService.getToken(authUserEntity);
        }

        // 모든 권한 가져온 후 Json형태로 변환
        String authoritiesJson = customConverter.convertAuthoritiesJson(authUserEntity);

        return new AuthenticationDetailsResponseDTO(
                authUserEntity.getId(),
                tokensDTO.getAccessToken(),
                tokensDTO.getRefreshToken(),
                newCsrfToken,
                authoritiesJson,
                tokenParsingDTO.needsUpdate()
        );
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
