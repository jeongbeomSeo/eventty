package com.eventty.authservice.application.service.Facade;

import com.eventty.authservice.api.ApiClient;
import com.eventty.authservice.api.dto.request.OAuthUserCreateApiRequestDTO;
import com.eventty.authservice.api.dto.request.UserCreateApiRequestDTO;
import com.eventty.authservice.api.dto.request.UserIdFindApiRequestDTO;
import com.eventty.authservice.api.dto.response.ImageQueryApiResponseDTO;
import com.eventty.authservice.applicaiton.dto.*;
import com.eventty.authservice.applicaiton.service.Facade.UserServiceImpl;
import com.eventty.authservice.applicaiton.service.subservices.*;
import com.eventty.authservice.applicaiton.service.subservices.factory.OAuthService;
import com.eventty.authservice.applicaiton.service.utils.CustomConverter;
import com.eventty.authservice.domain.Enum.OAuth;
import com.eventty.authservice.domain.Enum.UserRole;
import com.eventty.authservice.domain.entity.AuthUserEntity;
import com.eventty.authservice.domain.entity.AuthorityEntity;
import com.eventty.authservice.domain.entity.OAuthUserEntity;
import com.eventty.authservice.domain.exception.InvalidPasswordException;
import com.eventty.authservice.global.response.ResponseDTO;
import com.eventty.authservice.global.response.SuccessResponseDTO;
import com.eventty.authservice.presentation.dto.request.*;
import com.eventty.authservice.presentation.dto.response.AuthenticationDetailsResponseDTO;
import com.eventty.authservice.presentation.dto.response.PWFindResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import static com.eventty.authservice.application.service.utils.token.TokenTestUtil.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    @InjectMocks
    UserServiceImpl userService;
    @Mock
    UserDetailServiceImpl userDetailService;
    @Mock
    AuthServiceImpl authService;
    @Mock
    ApiClient apiClient;
    @Spy
    CustomConverter customConverter = new CustomConverter(new ObjectMapper());
    @Mock
    OAuthServiceFactory oAuthServiceFactory;

    @Nested
    class Login {

        // 유저 정보 Request
        private final Long userId = 1L;
        private final String email = "email";

        // Entity
        private final UserLoginRequestDTO userLoginRequestDTO = new UserLoginRequestDTO(email, "encrypted password");
        private final AuthUserEntity authUserEntity = createAuthUserEntity(userId, email);

        // Query
        private final ResponseEntity<ResponseDTO<ImageQueryApiResponseDTO>> successResponse = createResponseQueryImage();
        private final ResponseEntity<ResponseDTO<ImageQueryApiResponseDTO>> failResponse = createFailResponseQueryImage();

        // Token 관련
        private final SessionTokensDTO sessionTokensDTO = createSessionTokensDTO(email, userId);
        private final String csrfToken = "CSRF_TOKEN";
        @DisplayName("[성공] 로그인 성공")
        @Test
        void login_SUCCESS() {
            // Given
            // Email을 통한 유저 조회
            doReturn(authUserEntity).when(userDetailService).findAuthUser(userLoginRequestDTO.getEmail());
            // 유저 삭제 체크
            doNothing().when(userDetailService).validationUser(authUserEntity);
            // 비밀번호 매칭
            doReturn(true).when(authService).credentialMatch(userLoginRequestDTO, authUserEntity);
            // 이미지 API 요청
            doReturn(successResponse).when(apiClient).queryImageApi();
            // processLoginPostActions 동작
            doReturn(sessionTokensDTO).when(authService).getToken(authUserEntity);
            doReturn(true).when(authService).checkCsrfToken(authUserEntity.getId());
            doReturn(csrfToken).when(authService).getUpdateCsrfToken(authUserEntity.getId());

            // When
            LoginSuccessDTO loginSuccessDTO = userService.login(userLoginRequestDTO);

            // Then
            assertEquals(sessionTokensDTO.accessToken(), loginSuccessDTO.sessionTokensDTO().accessToken());
            assertEquals(userId, loginSuccessDTO.loginResponseDTO().getUserId());
            assertEquals(email, loginSuccessDTO.loginResponseDTO().getEmail());
        }

        // 일반 유저 로그인 검증
        @DisplayName("[성공] 로그인 성공 - 이미지 불러오기는 실패")
        @Test
        void login_SUCCESS_NOT_IMAGE() {
            // Given
            // Email을 통한 유저 조회
            doReturn(authUserEntity).when(userDetailService).findAuthUser(userLoginRequestDTO.getEmail());
            // 유저 삭제 체크
            doNothing().when(userDetailService).validationUser(authUserEntity);
            // 비밀번호 매칭
            doReturn(true).when(authService).credentialMatch(userLoginRequestDTO, authUserEntity);
            // 이미지 API 요청 - 가져오기 실패
            doReturn(failResponse).when(apiClient).queryImageApi();

            // processLoginPostActions 동작
            doReturn(sessionTokensDTO).when(authService).getToken(authUserEntity);
            doReturn(true).when(authService).checkCsrfToken(authUserEntity.getId());
            doReturn(csrfToken).when(authService).getUpdateCsrfToken(authUserEntity.getId());

            // When
            LoginSuccessDTO loginSuccessDTO = userService.login(userLoginRequestDTO);

            // Then
            assertNull(loginSuccessDTO.loginResponseDTO().getImageName());
            assertNull(loginSuccessDTO.loginResponseDTO().getImagePath());
        }

        @DisplayName("[실패] 로그인 실패 - 패스워드 불일치")
        @Test
        void login_FAIL_PASSWORD_NOT_EQUAL() {
            // Given
            doReturn(authUserEntity).when(userDetailService).findAuthUser(userLoginRequestDTO.getEmail());
            doNothing().when(userDetailService).validationUser(authUserEntity);
            doThrow(InvalidPasswordException.class).when(authService).credentialMatch(userLoginRequestDTO, authUserEntity);

            // When && Then
            assertThrows(InvalidPasswordException.class, () -> userService.login(userLoginRequestDTO));
        }

        // OAuth 사용자 로그인 검증
        @Mock
        private OAuthService oauthService;
        private final String NAVER_EMAIL = "email@naver.com";
        private final String socialName = OAuth.NAVER.getSocialName();
        private final AuthUserEntity authUserEntity_oauth = createAuthUserEntity(userId, NAVER_EMAIL);
        private final OAuthLoginRequestDTO oAuthLoginRequestDTO = new OAuthLoginRequestDTO("code");
        private final OAuthAccessTokenDTO oAuthAccessTokenDTO = new OAuthAccessTokenDTO("accessToken", "tokenType");
        private final OAuthUserInfoDTO oAuthUserInfoDTO = new OAuthUserInfoDTO("clientId", NAVER_EMAIL, "name", LocalDate.now(), "", "");
        @DisplayName("[성공] OAuth 로그인 성공 - 기존 회원")
        @Test
        void oauthLogin_SUCCESS() {
            // Given
            // 팩토리 패턴 동작
            doReturn(oauthService).when(oAuthServiceFactory).getOAuthService(socialName);
            // Access Token 가져오기
            doReturn(oAuthAccessTokenDTO).when(oauthService).getToken(oAuthLoginRequestDTO);
            // 유저 정보 받아오기
            doReturn(oAuthUserInfoDTO).when(oauthService).getUserInfo(oAuthAccessTokenDTO);
            // 기존 유저 회원인지 확인
            Optional<OAuthUserEntity> oauthUserEntityOpt = createOAuthUserEntityOpt(userId, socialName);
            doReturn(oauthUserEntityOpt).when(oauthService).findOAuthUserEntity(oAuthUserInfoDTO.clientId());
            doReturn(authUserEntity_oauth).when(userDetailService).findAuthUser(oauthUserEntityOpt.get().getUserId());
            // 유저 삭제 체크
            doNothing().when(userDetailService).validationUser(authUserEntity_oauth);
            // 이미지 API 요청
            doReturn(successResponse).when(apiClient).queryImageApi();

            // processLoginPostActions 동작
            doReturn(sessionTokensDTO).when(authService).getToken(authUserEntity_oauth);
            doReturn(true).when(authService).checkCsrfToken(authUserEntity_oauth.getId());
            doReturn(csrfToken).when(authService).getUpdateCsrfToken(authUserEntity_oauth.getId());

            // When
            LoginSuccessDTO loginSuccessDTO = userService.oauthLogin(oAuthLoginRequestDTO, socialName);

            // Then
            assertEquals(authUserEntity_oauth.getId(),loginSuccessDTO.loginResponseDTO().getUserId());
            assertEquals(NAVER_EMAIL, loginSuccessDTO.loginResponseDTO().getEmail());
        }
        @DisplayName("[성공] OAuth 로그인 - 회원가입")
        @Test
        void oauthLogin_SUCCESS_SIGN_UP() {
            // Given
            // 팩토리 패턴 동작
            doReturn(oauthService).when(oAuthServiceFactory).getOAuthService(socialName);
            // Access Token 가져오기
            doReturn(oAuthAccessTokenDTO).when(oauthService).getToken(oAuthLoginRequestDTO);
            // 유저 정보 받아오기
            doReturn(oAuthUserInfoDTO).when(oauthService).getUserInfo(oAuthAccessTokenDTO);
            // 기존 유저 회원인지 확인
            Optional<OAuthUserEntity> oauthUserEntityOpt = Optional.empty();
            doReturn(oauthUserEntityOpt).when(oauthService).findOAuthUserEntity(oAuthUserInfoDTO.clientId());

            // 신규 가입
            AuthUserEntity tempAuthUserEntity = AuthUserEntity.builder().email(oAuthUserInfoDTO.email()).password("").build();
            doReturn(tempAuthUserEntity).when(customConverter).convertAuthUserEntity(oAuthUserInfoDTO.email());

            doReturn(authUserEntity_oauth).when(userDetailService).create(tempAuthUserEntity, UserRole.USER);

            // Converter 작업은 @Spy이므로 넘어가기
            // 이미지 작업
            doReturn(successResponse).when(apiClient).createOAuthUserApi(any(OAuthUserCreateApiRequestDTO.class));

            // processLoginPostActions 동작
            doReturn(sessionTokensDTO).when(authService).getToken(authUserEntity_oauth);
            doReturn(true).when(authService).checkCsrfToken(authUserEntity_oauth.getId());
            doReturn(csrfToken).when(authService).getUpdateCsrfToken(authUserEntity_oauth.getId());

            // When
            LoginSuccessDTO loginSuccessDTO = userService.oauthLogin(oAuthLoginRequestDTO, socialName);

            // Then
            assertEquals(NAVER_EMAIL, loginSuccessDTO.loginResponseDTO().getEmail());
            verify(userDetailService, times(1)).create(tempAuthUserEntity, UserRole.USER);
            verify(oauthService, times(1)).create(any(OAuthUserEntity.class));
        }

        private ResponseEntity<ResponseDTO<ImageQueryApiResponseDTO>> createFailResponseQueryImage() {
            ResponseDTO<ImageQueryApiResponseDTO> responseDTO = new ResponseDTO<>();
            responseDTO.setIsSuccess(false);

            return ResponseEntity.ok(responseDTO);

        }
        private ResponseEntity<ResponseDTO<ImageQueryApiResponseDTO>> createResponseQueryImage() {
            ImageQueryApiResponseDTO imageQueryApiResponseDTO = new ImageQueryApiResponseDTO("originFileName", "imagePath");
            SuccessResponseDTO<ImageQueryApiResponseDTO> successResponseDTO = SuccessResponseDTO.of(imageQueryApiResponseDTO);
            ResponseDTO<ImageQueryApiResponseDTO> responseDTO = ResponseDTO.of(successResponseDTO);
            return ResponseEntity
                    .status(HttpStatusCode.valueOf(200))
                    .body(responseDTO);
        }
    }

    // 유저 검증
    @Nested
    class Authenticate{
        private final Long userId = 1L;
        private final String email = "email@mm.mm";
        private final String csrfValue = "CSRF_TOKEN";
        private final String newCsrfValue = "NEW_CSRF_VALUE";
        @DisplayName("[성공] 유저 검증 성공")
        @Test
        void authenticateUser() {
            // Given
            // 전처리
            UserAuthenticateRequestDTO userAuthenticateRequestDTO = createUserAuthenticateRequestDTO(userId, email, csrfValue);
            SessionTokensDTO sessionTokensDTO = new SessionTokensDTO(userAuthenticateRequestDTO.getAccessToken(), userAuthenticateRequestDTO.getRefreshToken());
            doReturn(sessionTokensDTO).when(customConverter).convertTokensDTO(userAuthenticateRequestDTO);

            // 검증
            AuthUserEntity authUserEntity = createAuthUserEntity(userId, email);
            AuthenticationResultDTO authenticationResultDTO = new AuthenticationResultDTO(authUserEntity, false);
            doReturn(authenticationResultDTO).when(authService).authenticate(sessionTokensDTO, userAuthenticateRequestDTO.getCsrfToken(), customConverter, userDetailService);

            // CSRF 토큰만 업데이트
            doReturn(newCsrfValue).when(authService).getUpdateCsrfToken(authUserEntity.getId());

            // When
            AuthenticationDetailsResponseDTO result = userService.authenticateUser(userAuthenticateRequestDTO);

            // Then
            assertNotEquals(csrfValue, result.getCsrfToken());
            assertEquals(newCsrfValue, result.getCsrfToken());
            assertEquals(userAuthenticateRequestDTO.getAccessToken(), result.getAccessToken());
            assertEquals(userAuthenticateRequestDTO.getRefreshToken(), result.getRefreshToken());
        }

        @DisplayName("[성공] 유저 검증 성공 - 모든 토큰 업데이트")
        @Test
        void authenticateUser_UPDATE_ALL_TOKEN() {
            // Given
            UserAuthenticateRequestDTO userAuthenticateRequestDTO_update = createUserAuthenticateRequestDTO_Update(userId, email, csrfValue);
            SessionTokensDTO sessionTokensDTO = createUpdateSessionTokensDTO(email, userId);
            doReturn(sessionTokensDTO).when(customConverter).convertTokensDTO(userAuthenticateRequestDTO_update);

            // 검증
            AuthUserEntity authUserEntity = createAuthUserEntity(userId, email);
            AuthenticationResultDTO authenticationResultDTO = new AuthenticationResultDTO(authUserEntity, true);
            doReturn(authenticationResultDTO).when(authService).authenticate(sessionTokensDTO, userAuthenticateRequestDTO_update.getCsrfToken(), customConverter, userDetailService);

            // 모든 토큰 업데이트
            doReturn(newCsrfValue).when(authService).getUpdateCsrfToken(authUserEntity.getId());
            doReturn(createNewSessionTokensDTO(email, userId)).when(authService).getToken(authUserEntity);

            // When
            AuthenticationDetailsResponseDTO result = userService.authenticateUser(userAuthenticateRequestDTO_update);

            // Then
            assertNotEquals(userAuthenticateRequestDTO_update.getAccessToken(), result.getAccessToken());
            assertNotEquals(userAuthenticateRequestDTO_update.getRefreshToken(), result.getRefreshToken());
        }

        @DisplayName("[실패] 유저 검증 실패 - 모든 세션 토큰 만료기간 지남")
        @Test
        void authenticateUser_FAIL() {
            // Given
            UserAuthenticateRequestDTO userAuthenticateRequestDTO_expired = createUserAuthenticateRequestDTO_Expired(userId, email, csrfValue);
            SessionTokensDTO sessionTokensDTO = createExpiredSessionTokensDTO(email, userId);
            doReturn(sessionTokensDTO).when(customConverter).convertTokensDTO(userAuthenticateRequestDTO_expired);

            doThrow(ExpiredJwtException.class).when(authService).authenticate(sessionTokensDTO, userAuthenticateRequestDTO_expired.getCsrfToken(), customConverter, userDetailService);

            // When && Then
            assertThrows(ExpiredJwtException.class, () -> userService.authenticateUser(userAuthenticateRequestDTO_expired));
        }
    }

    // 유저 패스워드 찾기에서 삭제된 유저가 안들어오는지 확인
    @DisplayName("[성공] 삭제된 계정과 삭제되지 않은 계정의 패스워드 찾기 요청")
    @Test
    void queryFindPW_FAIL() {
        // Given
        final String email = "email@mm.mm";
        final String name = "NAME";
        final String phone = "000-0000-0000";
        PWFindRequestDTO pwFindRequestDTO = new PWFindRequestDTO(email, name, phone);
        UserIdFindApiRequestDTO userIdFindApiRequestDTO = new UserIdFindApiRequestDTO(name, phone);
        doReturn(userIdFindApiRequestDTO).when(customConverter).convertUserIdFindApiRequestDTO(pwFindRequestDTO);

        List<Long> idList = createIdList(2);
        ResponseDTO<List<Long>> reponseDTO = ResponseDTO.of(SuccessResponseDTO.of(idList));
        ResponseEntity<ResponseDTO<List<Long>>> response = ResponseEntity.ok(reponseDTO);
        doReturn(response).when(apiClient).findUserIdApi(userIdFindApiRequestDTO);

        List<AuthUserEntity> authUserEntities = new ArrayList<>();
        final Long userId = 1L;
        authUserEntities.add(createAuthUserEntity(userId, "email@mm.mm"));
        doReturn(authUserEntities).when(userDetailService).findNotDeletedAuthUserList(response.getBody().getSuccessResponseDTO().getData());
        doReturn(true).when(authService).emailMatch(pwFindRequestDTO.getEmail(), authUserEntities.get(0));

        // When
        PWFindResponseDTO pwFindResponseDTO = userService.queryFindPW(pwFindRequestDTO);

        // Then
        assertEquals(userId, pwFindResponseDTO.getUserId());
    }

    @DisplayName("[성공] 회원 가입 성공")
    @Test
    void createUser() {
        // Given
        final String email = "신규 회원 이메일";
        final String password = "암호화 되지 않은 비밀번호";
        final String name = "회원 이름";
        final String phone = "000-0000-0000";
        final LocalDate birth = LocalDate.now();
        final String address = "회원 주소";
        FullUserCreateRequestDTO fullUserCreateRequestDTO = new FullUserCreateRequestDTO(email, password, name, phone, birth, address);
        final UserRole userRole = UserRole.USER;

        String encryptedPassword = "암호화 된 비밀번호";
        doReturn(encryptedPassword).when(authService).encryptePassword(password);

        AuthUserEntity authUserEntity = AuthUserEntity.builder().email(email).password(encryptedPassword).build();
        doReturn(authUserEntity).when(customConverter).convertAuthUserEntityConvert(email, encryptedPassword);

        final Long userId = 1L;
        AuthUserEntity authUserEntity_add_id_authorities = createAuthUserEntity(userId, email);
        doReturn(authUserEntity_add_id_authorities).when(userDetailService).create(authUserEntity, userRole);

        UserCreateApiRequestDTO userCreateApiRequestDTO = new UserCreateApiRequestDTO(userId, name, address, birth, phone);
        doReturn(userCreateApiRequestDTO).when(customConverter).convertUserCreateRequestDTO(fullUserCreateRequestDTO, authUserEntity_add_id_authorities.getId());

        ResponseEntity<ResponseDTO<Void>> response = ResponseEntity.ok(new ResponseDTO<>());
        doReturn(response).when(apiClient).createUserApi(userCreateApiRequestDTO);

        // When
        Long resultUserId = userService.createUser(fullUserCreateRequestDTO, userRole);

        // Then
        assertEquals(userId, resultUserId);
    }


    private List<Long> createIdList(int size) {
        List<Long> list = new ArrayList<>();
        for (int i = 1; i <= size; i++) {
            list.add((long)i);
        }

        return list;
    }

    private Optional<OAuthUserEntity> createOAuthUserEntityOpt(Long userId, String socialName) {
        return Optional.of(new OAuthUserEntity(1L, userId, "clientId", socialName));
    }
    private AuthUserEntity createAuthUserEntity(Long userId, String email) {
        AuthUserEntity result = AuthUserEntity.builder()
                .id(userId)
                .email(email)
                .password("encrypted password")
                .isDelete(false)
                .build();

        List<AuthorityEntity> authorities = new ArrayList<>();
        authorities.add(AuthorityEntity.builder()
                .id(1L)
                .name(UserRole.USER.getRole())
                .authUserEntity(result)
                .build());

        result.setAuthorities(authorities);

        return result;
    }

}
