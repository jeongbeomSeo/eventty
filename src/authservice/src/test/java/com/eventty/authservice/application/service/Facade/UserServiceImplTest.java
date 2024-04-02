package com.eventty.authservice.application.service.Facade;

import com.eventty.authservice.api.ApiClient;
import com.eventty.authservice.api.dto.request.OAuthUserCreateApiRequestDTO;
import com.eventty.authservice.api.dto.response.ImageQueryApiResponseDTO;
import com.eventty.authservice.applicaiton.dto.LoginSuccessDTO;
import com.eventty.authservice.applicaiton.dto.OAuthAccessTokenDTO;
import com.eventty.authservice.applicaiton.dto.OAuthUserInfoDTO;
import com.eventty.authservice.applicaiton.dto.SessionTokensDTO;
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
import com.eventty.authservice.presentation.dto.request.OAuthLoginRequestDTO;
import com.eventty.authservice.presentation.dto.request.UserLoginRequestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    // 유저 패스워드 찾기에서 삭제된 유저가 안들어오는지 확인

    // 회원가입의 경우 연속 요청이 들어오는 경우 처리하는 로직 구성

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
