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
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.util.Date;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class TokenProviderTest {
    private final String ISSUER = "ISSUER";
    private final String SECRET_KEY = "secret key";
    private final Long VALID_EXPIRED_TIME = 2L;
    private final Long UNVALID_EXPIRED_TIME = -10L;

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
        final Long id = 1L;
        final Long userId = 1L;
        final String name = "CSRF_TOKEN";
        final Optional<CsrfTokenEntity> expectedCsrfTokenEntity = createCsrfTokenEntity(id, userId, name);
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
        doThrow(new CsrfTokenNotFoundException(userId)).when(csrfTokenProvider).get(userId);

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
            doThrow(new InValidRefreshTokenException(validateRefreshTokenDTO))
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

    private SessionTokensDTO createExpiredSessionTokensDTO(String email, Long userId) {
        return new SessionTokensDTO(createToken(email, userId, UNVALID_EXPIRED_TIME), createToken(email, userId, UNVALID_EXPIRED_TIME));
    }
    private SessionTokensDTO createUpdateSessionTokensDTO(String email, Long userId) {
        return new SessionTokensDTO(createToken(email, userId, UNVALID_EXPIRED_TIME), createToken(email, userId, VALID_EXPIRED_TIME));
    }
    private SessionTokensDTO createSessionTokensDTO(String email, Long userId) {

        return new SessionTokensDTO(createToken(email, userId, VALID_EXPIRED_TIME), createToken(email, userId, VALID_EXPIRED_TIME));
    }
    private String createToken(String email, Long userId, Long expired_time) {
        Claims claims = Jwts.claims().setSubject(email);

        claims.put(TokenEnum.USERID.getName(), userId);

        Date now = new Date();
        Duration expiredAt = Duration.ofHours(expired_time);

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setHeaderParam("alg", "HS256")
                .addClaims(claims)
                .setIssuer(ISSUER)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + expiredAt.toMillis()))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    private Optional<CsrfTokenEntity> createCsrfTokenEntity(Long id, Long userId, String name) {
        return Optional.of(CsrfTokenEntity.builder()
                .id(id)
                .userId(userId)
                .name(name)
                .build());
    }

}
