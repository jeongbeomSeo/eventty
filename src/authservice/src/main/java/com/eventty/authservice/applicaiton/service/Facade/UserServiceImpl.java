package com.eventty.authservice.applicaiton.service.Facade;

import com.eventty.authservice.api.dto.request.UserIdFindApiRequestDTO;
import com.eventty.authservice.api.dto.response.ImageQueryApiResponseDTO;
import com.eventty.authservice.applicaiton.dto.*;
import com.eventty.authservice.applicaiton.service.subservices.AuthService;
import com.eventty.authservice.applicaiton.service.subservices.AuthServiceImpl;
import com.eventty.authservice.domain.exception.UserNotFoundException;
import com.eventty.authservice.domain.model.Authority;
import com.eventty.authservice.global.response.ResponseDTO;
import com.eventty.authservice.infrastructure.contextholder.UserContextHolder;
import com.eventty.authservice.presentation.dto.request.*;
import com.eventty.authservice.presentation.dto.response.AuthenticationDetailsResponseDTO;
import com.eventty.authservice.presentation.dto.response.EmailFindResponseDTO;
import com.eventty.authservice.presentation.dto.response.PWFindResponseDTO;
import jakarta.transaction.Transactional;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.eventty.authservice.api.ApiClient;
import com.eventty.authservice.api.dto.request.UserCreateApiRequestDTO;
import com.eventty.authservice.applicaiton.service.subservices.UserDetailService;
import com.eventty.authservice.applicaiton.service.subservices.UserDetailServiceImpl;
import com.eventty.authservice.domain.Enum.UserRole;
import com.eventty.authservice.domain.entity.AuthUserEntity;
import com.eventty.authservice.applicaiton.service.utils.CustomConverter;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserDetailService userDetailService;
    private final AuthService authService;
    private final ApiClient apiClient;
    private final CustomConverter customConverter;

    @Autowired
    public UserServiceImpl(UserDetailServiceImpl userServiceImpl,
                           AuthServiceImpl authServiceImpl,
                           @Lazy ApiClient apiClient,
                           @Lazy CustomConverter converterService) {
        this.userDetailService = userServiceImpl;
        this.authService = authServiceImpl;
        this.apiClient = apiClient;
        this.customConverter = converterService;
    }

    @Override
    @Transactional
    public LoginSuccessDTO login(UserLoginRequestDTO userLoginRequestDTO) {

        // email을 이용해서 user 조회
        AuthUserEntity authUserEntity = userDetailService.findAuthUser(userLoginRequestDTO.getEmail());

        // 유저 삭제되어 있는지 확인
        userDetailService.validationUser(authUserEntity);

        // 비밀번호 매칭
        authService.credentialMatch(userLoginRequestDTO, authUserEntity);

        Long userId = authUserEntity.getId();

        // 모든 과정 성공시 User Context 업데이트
        List<Authority> authorities = customConverter.convertAuthority(authUserEntity);
        UserContextHolder.getContext().setUserId(String.valueOf(userId));
        UserContextHolder.getContext().setAuthorities(authorities);

        // 이미지 불러오기
        ResponseEntity<ResponseDTO<ImageQueryApiResponseDTO>> reponse =
                apiClient.queryImageApi();

        ImageQueryApiResponseDTO imageQueryApiResponseDTO;
        if (reponse.hasBody() && Objects.requireNonNull(reponse.getBody()).isSuccess())
            imageQueryApiResponseDTO = reponse.getBody().getSuccessResponseDTO().getData();
        else
            imageQueryApiResponseDTO = new ImageQueryApiResponseDTO();

        // 모든 과정 성공시 JWT, Refresh Token과 email, 권한을 각각 DTO에 담아서 LoginSuccessDTO에 담아서 반환 => 권한 X 역할만 담기
        SessionTokensDTO sessionTokensDTO = authService.getToken(authUserEntity);

        // 로그인을 했을 때 DB에 토큰이 있을 수 있고, 없을 수 있으니 구분져서 로직 구성
        String csrfToken = authService.checkCsrfToken(userId) ?
                authService.getUpdateCsrfToken(userId) : authService.getNewCsrfToken(userId);

        return customConverter
                .convertLoginSuccessDTO(sessionTokensDTO, authUserEntity, csrfToken, imageQueryApiResponseDTO);
    }

    @Override
    @Transactional
    public Long createUser(FullUserCreateRequestDTO fullUserCreateRequestDTO, UserRole role) {

        // 전달 받은 DTO로 Entity로 변환
        String email = fullUserCreateRequestDTO.getEmail();
        String encryptedPassword = authService.encryptePassword(fullUserCreateRequestDTO.getPassword());
        AuthUserEntity authUserEntity = customConverter.convertAuthEntityConvert(email, encryptedPassword);

        // 유저 생성
        Long userId = userDetailService.create(authUserEntity, role);

        // API 요청 로직
        UserCreateApiRequestDTO userCreateApiRequestDTO = customConverter.convertUserCreateRequestDTO(fullUserCreateRequestDTO, userId);
        apiClient.createUserApi(userCreateApiRequestDTO);

        return userId;
    }

    @Override
    public void validateEmailNotDuplicated(String email) {
        userDetailService.validateEmail(email);
    }

    // 유저 삭제의 경우 토큰을 업데이트 해줄 필요가 없나? => 트래픽에 의한 요청 실패와 같은 경우를 고려해봤을 때, 엄데이트를 해줘 보내야 하지 않나 생각함
    @Override
    @Transactional
    public Long deleteUser(SessionTokensDTO sessionTokensDTO, String csrfToken) {

        // 검증
        AuthenticationResultDTO authenticationResultDTO = authService.authenticate(
                sessionTokensDTO, csrfToken, customConverter, userDetailService);

        // 가독성을 위해서 꺼내서 활용 => needsUpdate는 필요 없지만 일단은 DTO 재활용
        AuthUserEntity authUserEntity = authenticationResultDTO.authUserEntity();

        // 모든 토큰 삭제 후
        authService.deleteAllToken(authUserEntity.getId());

        // 유저 삭제
        return userDetailService.delete(authUserEntity);
    }

    @Override
    @Transactional
    public AuthenticationDetailsResponseDTO authenticateUser(UserAuthenticateRequestDTO userAuthenticateRequestDTO) {

        // 검증하기 전에 JWT, Refresh Token은 TokensDTO로 묶어주기
        SessionTokensDTO sessionTokensDTO = customConverter.convertTokensDTO(userAuthenticateRequestDTO);

        // 검증
        AuthenticationResultDTO authenticationResultDTO = authService.authenticate(
                sessionTokensDTO, userAuthenticateRequestDTO.getCsrfToken(), customConverter, userDetailService);

        // 가독성을 위해서 꺼내서 활용
        AuthUserEntity authUserEntity = authenticationResultDTO.authUserEntity();
        boolean TokensNeedUpdate = authenticationResultDTO.needsUpate();

        // 모든 검증을 마친 후 토큰 업데이트가 필요하면 수행
        String newCsrfToken = authService.getUpdateCsrfToken(authUserEntity.getId());

        if (TokensNeedUpdate) {
            // 검증 로직 없이 새로운 토큰 가져오기
            sessionTokensDTO = authService.getToken(authenticationResultDTO.authUserEntity());
        }

        // 모든 권한 가져온 후 Json형태로 변환
        String authoritiesJson = customConverter.convertAuthoritiesJson(authUserEntity);

        return new AuthenticationDetailsResponseDTO(
                authUserEntity.getId(),
                sessionTokensDTO.accessToken(),
                sessionTokensDTO.refreshToken(),
                newCsrfToken,
                authoritiesJson,
                TokensNeedUpdate
        );
    }

    @Override
    public Long logout(SessionTokensDTO sessionTokensDTO, String csrfToken) {

        // 검증
        AuthenticationResultDTO authenticationResultDTO = authService.authenticate(
                sessionTokensDTO, csrfToken, customConverter, userDetailService);

        // 가독성을 위해서 꺼내서 활용 => needsUpdate는 필요 없지만 일단은 DTO 재활용
        AuthUserEntity authUserEntity = authenticationResultDTO.authUserEntity();

        // 모든 토큰 삭제 후
        authService.deleteAllToken(authUserEntity.getId());

        // 유저 아이디 반환
        return authUserEntity.getId();
    }

    @Override
    public CsrfTokenDTO changePW(PWChangeRequestDTO pwChangeRequestDTO, SessionTokensDTO sessionTokensDTO, String csrfToken) {

        // 검증
        AuthenticationResultDTO authenticationResultDTO = authService.authenticate(
                sessionTokensDTO, csrfToken, customConverter, userDetailService
        );

        // 가독성을 위해서 꺼내서 활용
        AuthUserEntity authUserEntity = authenticationResultDTO.authUserEntity();

        // 비밀번호 변경
        String encryptedPassword = authService.encryptePassword(pwChangeRequestDTO.getPassword());
        authUserEntity = userDetailService.changePwAuthUser(encryptedPassword, authUserEntity);

        // CSRF Token Update
        String newCsrfToken = authService.getUpdateCsrfToken(authUserEntity.getId());

        return new CsrfTokenDTO(authUserEntity.getId(), newCsrfToken);
    }

    @Override
    public List<EmailFindResponseDTO> queryFindEmail(EmailFindRequestDTO emailFindRequestDTO) {

        // API 요청을 통해서 User Id 가져오기
        UserIdFindApiRequestDTO userIdFindApiRequestDTO
                = customConverter.convertUserIdFindApiRequestDTO(emailFindRequestDTO);

        List<Long> userIdList = getUserIdList(userIdFindApiRequestDTO);

        // 삭제되지 않은 유저 이메일 정보 모두 가져오기
        List<AuthUserEntity> authUserEntities = userDetailService.findNotDeletedAuthUserList(userIdList);

        return authUserEntities.stream()
                .map(customConverter::convertEmailFindResponseDTO)
                .toList();

    }

    @Override
    public PWFindResponseDTO queryFindPW(PWFindRequestDTO pwFindRequestDTO) {

        // API 요청을 통해서 유저 아이디 받아오기
        UserIdFindApiRequestDTO userIdFindApiRequestDTO
                = customConverter.convertUserIdFindApiRequestDTO(pwFindRequestDTO);

        List<Long> userIdList = getUserIdList(userIdFindApiRequestDTO);

        // 삭제되지 않은 유저 이메일 정보 모두 가져오기
        List<AuthUserEntity> authUserEntities = userDetailService.findNotDeletedAuthUserList(userIdList);

        // 이메일 검증
        AuthUserEntity authUserEntity = authUserEntities.stream()
                .filter(authuUserEntiy -> authService.emailMatch(pwFindRequestDTO.getEmail(), authuUserEntiy))
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException(pwFindRequestDTO.getEmail(), "email"));

        return customConverter.convertPWFindResponseDTO(authUserEntity);
    }

    private List<Long> getUserIdList(UserIdFindApiRequestDTO userIdFindApiRequestDTO) {
        ResponseEntity<ResponseDTO<List<Long>>> response =
                apiClient.findUserIdApi(userIdFindApiRequestDTO);

        if (!response.getBody().isSuccess())
            throw new UserNotFoundException(userIdFindApiRequestDTO.getName(), "name");

        return response.getBody().getSuccessResponseDTO().getData();
    }
}
