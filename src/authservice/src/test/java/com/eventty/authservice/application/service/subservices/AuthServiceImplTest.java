package com.eventty.authservice.application.service.subservices;

import com.eventty.authservice.applicaiton.dto.AuthenticationResultDTO;
import com.eventty.authservice.applicaiton.dto.CsrfTokenDTO;
import com.eventty.authservice.applicaiton.dto.SessionTokensDTO;
import com.eventty.authservice.applicaiton.dto.TokenParsingDTO;
import com.eventty.authservice.applicaiton.service.subservices.AuthServiceImpl;
import com.eventty.authservice.applicaiton.service.subservices.UserDetailService;
import com.eventty.authservice.applicaiton.service.utils.CustomConverter;
import com.eventty.authservice.applicaiton.service.utils.CustomPasswordEncoder;
import com.eventty.authservice.applicaiton.service.utils.token.TokenProvider;
import com.eventty.authservice.domain.entity.AuthUserEntity;
import com.eventty.authservice.domain.exception.InvalidCsrfTokenException;
import io.jsonwebtoken.ExpiredJwtException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.eventty.authservice.application.service.utils.token.TokenTestUtil.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceImplTest {

    @InjectMocks
    AuthServiceImpl authService;
    @Mock
    TokenProvider tokenProvider;
    @Mock
    CustomPasswordEncoder customPasswordEncoder;

    @DisplayName("[성공] CSRF 유효성 검증 성공")
    @Test
    void csrfTokenValidationCheck_SUCCESS() {
        // Given
        final String value = "CSRF_TOKEN_VALUE";
        final Long userId = 1L;
        CsrfTokenDTO csrfTokenDTO = new CsrfTokenDTO(userId, value);

        doReturn(value).when(tokenProvider).getCsrfToken(csrfTokenDTO.userId());

        // When
        authService.csrfTokenValidationCheck(csrfTokenDTO);

        // Then
        verify(tokenProvider, times(1)).getCsrfToken(csrfTokenDTO.userId());
    }

    @DisplayName("[실패] CSRF 유효성 검증 실패")
    @Test
    void csrfTokenValidationCheck_FAIL() {
        // Given
        final String value = "CSRF_TOKEN_VALUE";
        final Long userId = 1L;
        CsrfTokenDTO csrfTokenDTO = new CsrfTokenDTO(userId, value);

        doReturn("NOT EQUAL").when(tokenProvider).getCsrfToken(csrfTokenDTO.userId());

        // When && Then
        assertThrows(InvalidCsrfTokenException.class, () -> authService.csrfTokenValidationCheck(csrfTokenDTO));
    }

    @Nested
    class AuthenticateTest {

        @Mock
        CustomConverter converter;
        @Mock
        UserDetailService userDetailService;
        private final String email = "email";
        private final Long userId = 1L;
        private final String csrfToken = "CSRF_TOKEN";
        private final AuthUserEntity authUserEntity = AuthUserEntity.builder().id(userId).email(email).isDelete(false).build();


        @DisplayName("[성공] 전체 검증 성공")
        @Test
        void authenticate_SUCCESS() {
            // Given
            SessionTokensDTO sessionTokensDTO = createSessionTokensDTO(email, userId);
            CsrfTokenDTO csrfTokenDTO = new CsrfTokenDTO(userId, csrfToken);

            TokenParsingDTO tokenParsingDTO = new TokenParsingDTO(userId, false);
            doReturn(tokenParsingDTO).when(tokenProvider).parsingToken(sessionTokensDTO);

            doReturn(authUserEntity).when(userDetailService).findAuthUser(userId);
            doReturn(csrfTokenDTO).when(converter).convertCsrfTokenDTO(userId, csrfToken);
            doReturn(csrfToken).when(tokenProvider).getCsrfToken(csrfTokenDTO.userId());

            // When
            AuthenticationResultDTO authenticationResultDTO = authService.authenticate(sessionTokensDTO, csrfToken, converter, userDetailService);

            // Then
            assertEquals(email, authenticationResultDTO.AuthUserEntity().getEmail());
            assertEquals(userId, authenticationResultDTO.AuthUserEntity().getId());
            assertFalse(authenticationResultDTO.needsUpate());
        }

        @DisplayName("[성공] 전체 검증 성공 - 액세스 토큰이 업데이트 필요한 경우 및 CSRF 토큰 업데이트 유무 확인")
        @Test
        void authenticate_UPDATE() {
            SessionTokensDTO sessionTokensDTO = createUpdateSessionTokensDTO(email, userId);
            CsrfTokenDTO csrfTokenDTO = new CsrfTokenDTO(userId, csrfToken);

            TokenParsingDTO tokenParsingDTO = new TokenParsingDTO(userId, true);
            doReturn(tokenParsingDTO).when(tokenProvider).parsingToken(sessionTokensDTO);

            doReturn(authUserEntity).when(userDetailService).findAuthUser(userId);
            doReturn(csrfTokenDTO).when(converter).convertCsrfTokenDTO(userId, csrfToken);
            doReturn(csrfToken).when(tokenProvider).getCsrfToken(csrfTokenDTO.userId());

            // When
            AuthenticationResultDTO authenticationResultDTO = authService.authenticate(sessionTokensDTO, csrfToken, converter, userDetailService);

            // Then
            assertTrue(authenticationResultDTO.needsUpate());
        }

        @DisplayName("[실패] 전체 검증 실패 - 액세스 토큰 및 리프레시 토큰 유효 기간 만료")
        @Test
        void authenticate_FAIL_EXPIRED() {
            // Given
            SessionTokensDTO sessionTokensDTO = createExpiredSessionTokensDTO(email, userId);
            doThrow(ExpiredJwtException.class).when(tokenProvider).parsingToken(sessionTokensDTO);

            // When && Then
            assertThrows(Exception.class, () -> authService.authenticate(sessionTokensDTO, csrfToken, converter, userDetailService));
        }
    }

}
