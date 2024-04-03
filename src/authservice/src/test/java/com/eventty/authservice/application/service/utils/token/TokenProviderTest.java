package com.eventty.authservice.application.service.utils.token;

import com.eventty.authservice.applicaiton.dto.SessionTokensDTO;
import com.eventty.authservice.applicaiton.dto.TokenParsingDTO;
import com.eventty.authservice.applicaiton.dto.ValidateRefreshTokenDTO;
import com.eventty.authservice.applicaiton.service.utils.CustomConverter;
import com.eventty.authservice.applicaiton.service.utils.token.*;
import com.eventty.authservice.domain.entity.AuthUserEntity;
import com.eventty.authservice.domain.entity.CsrfTokenEntity;
import com.eventty.authservice.domain.entity.RefreshTokenEntity;
import com.eventty.authservice.domain.exception.CsrfTokenNotFoundException;
import com.eventty.authservice.domain.exception.InValidRefreshTokenException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import static com.eventty.authservice.application.service.utils.token.TokenTestUtil.*;

@ExtendWith(MockitoExtension.class)
public class TokenProviderTest {

    @InjectMocks
    TokenProvider tokenProvider;
    @Mock
    TokenProperties tokenProperties;
    @Mock
    AccessTokenProvider accessTokenProvider;
    @Mock
    RefreshTokenProvider refreshTokenProvider;
    @Mock
    CSRFTokenProvider csrfTokenProvider;
    @Mock
    CustomConverter customConverter;

    @DisplayName("[성공]CSRF 토큰 가져오기")
    @Test
    void getCsrfToken_SUCCESS() {
        // Given
        final Long userId = 1L;
        final String name = "CSRF_TOKEN";
        final Optional<CsrfTokenEntity> expectedCsrfTokenEntity = createCsrfTokenEntity(userId, name);
        doReturn(expectedCsrfTokenEntity).when(csrfTokenProvider).get(userId);

        // When
        String result = tokenProvider.getCsrfToken(userId);

        // Then
        assertEquals(expectedCsrfTokenEntity.get().getName(), result);
    }

    @DisplayName("[실패]CSRF 토큰 가져오기")
    @Test
    void getCsrfToken_FAIL() {
        // Given
        final Long userId = 1L;
        doThrow(CsrfTokenNotFoundException.class).when(csrfTokenProvider).get(userId);

        // When && Then
        assertThrows(CsrfTokenNotFoundException.class, () -> tokenProvider.getCsrfToken(userId));
    }

    @Nested
    class ParsingGroup {
        @BeforeEach
        void setUp() {
            Mockito.when(tokenProperties.getIssuer()).thenReturn(ISSUER);
            Mockito.when(tokenProperties.getSecretKey()).thenReturn(SECRET_KEY);
        }

        @DisplayName("Token Parsing 성공")
        @Test
        void parsingToken_SUCCESS() {
            // Given
            final String email = "email";
            final Long userId = 1L;
            SessionTokensDTO sessionTokensDTO = createSessionTokensDTO(email, userId);

            // When
            TokenParsingDTO tokenParsingDTO = tokenProvider.parsingToken(sessionTokensDTO);

            // Then
            assertEquals(userId, tokenParsingDTO.userId());
        }

        @DisplayName("[성공] Token Parsing 업데이트")
        @Test
        void parsingToken_UPDATE() {
            // Given
            final String email = "email";
            final Long userId = 1L;
            SessionTokensDTO sessionTokensDTO = createUpdateSessionTokensDTO(email, userId);

            // When
            TokenParsingDTO tokenParsingDTO = tokenProvider.parsingToken(sessionTokensDTO);

            // Then
            assertEquals(userId, tokenParsingDTO.userId());
        }
        @DisplayName("[실패] Token Parsing 실패 - 모든 토큰 유효기간 지남")
        @Test
        void parsingToken_FAIL_Expired() {
            // Given
            final String email = "email";
            final Long userId = 1L;
            SessionTokensDTO sessionTokensDTO = createExpiredSessionTokensDTO(email, userId);

            // When && Then
            assertThrows(Exception.class, () -> tokenProvider.parsingToken(sessionTokensDTO));
        }

        @DisplayName("[실패] Token Parsing 실패 - Refresh Token이 유효하지 않는 경우")
        @Test
        void parsingToken_FAIL_UNVALID() {
            // Given
            final String email = "email";
            final Long userId = 1L;
            SessionTokensDTO sessionTokensDTO = createUpdateSessionTokensDTO(email, userId);
            ValidateRefreshTokenDTO validateRefreshTokenDTO = new ValidateRefreshTokenDTO(userId, sessionTokensDTO.refreshToken());

            // When
            doReturn(validateRefreshTokenDTO).when(customConverter).convertToValidationRefreshTokenDTO(userId, sessionTokensDTO);
            doThrow(InValidRefreshTokenException.class)
                    .when(refreshTokenProvider).validationCheck(validateRefreshTokenDTO);

            // Then
            assertThrows(InValidRefreshTokenException.class, () -> tokenProvider.parsingToken(sessionTokensDTO));
        }
    }

    @DisplayName("[성공] 모든 Session 토큰 가져오기")
    @Test
    void getAllToken() {
        // Given
        String accessToken = "access_token";
        String refreshToken = "refresh_token";

        doReturn(accessToken).when(accessTokenProvider).generateToken(any(AuthUserEntity.class), any(Date.class), any(Date.class));
        doReturn(refreshToken).when(refreshTokenProvider).generate(any(AuthUserEntity.class), any(Date.class), any(Date.class));

        doReturn(new RefreshTokenEntity(1L, 1L, "name")).when(refreshTokenProvider).saveOrUpdate(any(String.class), any(Long.class));

        // When
        SessionTokensDTO sessionTokensDTO = tokenProvider.getAllToken(AuthUserEntity.builder().id(1L).email("asdf@asdef").isDelete(false).build());

        // Then
        assertEquals(accessToken, sessionTokensDTO.accessToken());
        assertEquals(refreshToken, sessionTokensDTO.refreshToken());

        verify(refreshTokenProvider, times(1)).saveOrUpdate(any(String.class), any(Long.class));
    }
}
