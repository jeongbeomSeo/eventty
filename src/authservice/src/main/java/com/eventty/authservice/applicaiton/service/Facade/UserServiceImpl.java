package com.eventty.authservice.applicaiton.service.Facade;

import com.eventty.authservice.applicaiton.dto.*;
import com.eventty.authservice.applicaiton.service.subservices.AuthService;
import com.eventty.authservice.applicaiton.service.subservices.AuthServiceImpl;
import com.eventty.authservice.presentation.dto.request.AuthenticationUserRequestDTO;
import com.eventty.authservice.presentation.dto.request.ChangePWRequestDTO;
import com.eventty.authservice.presentation.dto.request.UserLoginRequestDTO;
import com.eventty.authservice.presentation.dto.response.AuthenticationDetailsResponseDTO;
import jakarta.servlet.http.Cookie;
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
    @Transactional
    public LoginSuccessDTO login(UserLoginRequestDTO userLoginRequestDTO) {

        // email을 이용해서 user 조회
        AuthUserEntity authUserEntity = userDetailService.findAuthUser(userLoginRequestDTO.email());

        // 유저 삭제되어 있는지 확인
        userDetailService.validationUser(authUserEntity);

        // 비밀번호 매칭
        authService.credentialMatch(userLoginRequestDTO, authUserEntity, customPasswordEncoder);

        Long userId = authUserEntity.getId();

        // 모든 과정 성공시 JWT, Refresh Token과 email, 권한을 각각 DTO에 담아서 LoginSuccessDTO에 담아서 반환 => 권한 X 역할만 담기
        TokensDTO tokensDTO = authService.getToken(authUserEntity);
        // 로그인을 했을 때 DB에 토큰이 있을 수 있고, 없을 수 있으니 구분져서 로직 구성
        String csrfToken = authService.checkCsrfToken(userId) ?
                authService.getUpdateCsrfToken(userId) : authService.getNewCsrfToken(userId);

        return customConverter
                .convertLoginSuccessDTO(tokensDTO, authUserEntity, csrfToken);
    }

    @Override
    @Transactional
    public Long createUser(FullUserCreateRequestDTO fullUserCreateRequestDTO, UserRole role) {

        // 전달 받은 DTO로 Entity로 변환
        AuthUserEntity authUserEntity = customConverter.convertAuthEntityConvert(fullUserCreateRequestDTO, customPasswordEncoder);

        // 유저 생성
        Long userId = userDetailService.create(authUserEntity, role);

        // API 요청 로직
        UserCreateRequestDTO userCreateRequestDTO = customConverter.convertUserCreateRequestDTO(fullUserCreateRequestDTO, userId);
        apiClient.createUserApi(userCreateRequestDTO);

        return userId;
    }

    @Override
    public void validateEmailNotDuplicated(String email) {
        userDetailService.validateEmail(email);
    }

    // 유저 삭제의 경우 토큰을 업데이트 해줄 필요가 없나? => 트래픽에 의한 요청 실패와 같은 경우를 고려해봤을 때, 엄데이트를 해줘 보내야 하지 않나 생각함
    @Override
    @Transactional
    public Long deleteUser(Cookie[] cookies, String csrfToken) {

        // 검증하기 전에 JWT, Refresh Token은 TokensDTO로 묶어주기
        TokensDTO tokensDTO = customConverter.convertTokensDTO(cookies);

        // 검증
        AuthenticationResultDTO authenticationResultDTO = authService.authenticate(
                tokensDTO, csrfToken, customConverter, userDetailService);

        // 가독성을 위해서 꺼내서 활용 => needsUpdate는 필요 없지만 일단은 DTO 재활용
        AuthUserEntity authUserEntity = authenticationResultDTO.authUserEntity();

        // 모든 토큰 삭제 후
        authService.deleteAllToken(authUserEntity.getId());

        // 유저 삭제
        return userDetailService.delete(authUserEntity);
    }

    @Override
    @Transactional
    public AuthenticationDetailsResponseDTO authenticateUser(AuthenticationUserRequestDTO authenticationUserRequestDTO) {

        // 검증하기 전에 JWT, Refresh Token은 TokensDTO로 묶어주기
        TokensDTO tokensDTO = customConverter.convertTokensDTO(authenticationUserRequestDTO);

        // 검증
        AuthenticationResultDTO authenticationResultDTO = authService.authenticate(
                tokensDTO, authenticationUserRequestDTO.csrfToken(), customConverter, userDetailService);

        // 가독성을 위해서 꺼내서 활용
        AuthUserEntity authUserEntity = authenticationResultDTO.authUserEntity();
        boolean TokensNeedUpdate = authenticationResultDTO.needsUpate();

        // 모든 검증을 마친 후 토큰 업데이트가 필요하면 수행
        String newCsrfToken = authService.getUpdateCsrfToken(authUserEntity.getId());

        if (TokensNeedUpdate) {
            // 검증 로직 없이 새로운 토큰 가져오기
            tokensDTO = authService.getToken(authenticationResultDTO.authUserEntity());
        }

        // 모든 권한 가져온 후 Json형태로 변환
        String authoritiesJson = customConverter.convertAuthoritiesJson(authUserEntity);

        return new AuthenticationDetailsResponseDTO(
                authUserEntity.getId(),
                tokensDTO.accessToken(),
                tokensDTO.refreshToken(),
                newCsrfToken,
                authoritiesJson,
                TokensNeedUpdate
        );
    }

    @Override
    public Long logout(Cookie[] cookies, String csrfToken) {
        // 검증하기 전에 JWT, Refresh Token은 TokensDTO로 묶어주기
        TokensDTO tokensDTO = customConverter.convertTokensDTO(cookies);

        // 검증
        AuthenticationResultDTO authenticationResultDTO = authService.authenticate(
                tokensDTO, csrfToken, customConverter, userDetailService);

        // 가독성을 위해서 꺼내서 활용 => needsUpdate는 필요 없지만 일단은 DTO 재활용
        AuthUserEntity authUserEntity = authenticationResultDTO.authUserEntity();

        // 모든 토큰 삭제 후
        authService.deleteAllToken(authUserEntity.getId());

        // 유저 아이디 반환
        return authUserEntity.getId();
    }

    @Override
    public CsrfTokenDTO changePW(ChangePWRequestDTO changePWRequestDTO, Cookie[] cookies, String csrfToken) {

        // 검증하기 전에 JWT, Refresh Token은 TokensDTO로 묶어주기
        TokensDTO tokensDTO = customConverter.convertTokensDTO(cookies);

        // 검증
        AuthenticationResultDTO authenticationResultDTO = authService.authenticate(
                tokensDTO, csrfToken, customConverter, userDetailService
        );

        // 가독성을 위해서 꺼내서 활용
        AuthUserEntity authUserEntity = authenticationResultDTO.authUserEntity();

        // 비밀번호 변경
        authUserEntity = userDetailService.changePwAuthUser(changePWRequestDTO, authUserEntity, customPasswordEncoder);

        // CSRF Token Update
        String newCsrfToken = authService.getUpdateCsrfToken(authUserEntity.getId());

        return new CsrfTokenDTO(authUserEntity.getId(), newCsrfToken);
    }
}
