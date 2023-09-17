package com.eventty.authservice.applicaiton.service.utils;

import com.eventty.authservice.api.dto.UserCreateRequestDTO;
import com.eventty.authservice.applicaiton.dto.CsrfTokenDTO;
import com.eventty.authservice.applicaiton.dto.LoginSuccessDTO;
import com.eventty.authservice.applicaiton.dto.TokensDTO;
import com.eventty.authservice.applicaiton.dto.ValidateRefreshTokenDTO;
import com.eventty.authservice.applicaiton.service.utils.token.TokenEnum;
import com.eventty.authservice.domain.entity.AuthUserEntity;
import com.eventty.authservice.domain.entity.AuthorityEntity;
import com.eventty.authservice.domain.exception.PermissionDeniedException;
import com.eventty.authservice.domain.model.Authority;
import com.eventty.authservice.presentation.dto.request.AuthenticationUserRequestDTO;
import com.eventty.authservice.presentation.dto.request.FullUserCreateRequestDTO;
import com.eventty.authservice.presentation.dto.response.LoginResponseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomConverter {

    private final ObjectMapper objectMapper;

    // 유저 인증 요청할 때 필요한 데이터를 전부 모아둔 DTO 생성
    public TokensDTO convertTokensDTO(Cookie[] cookies) {

        String accessToken = getJwt(cookies);
        String refreshToken = getRefreshToken(cookies);

        return new TokensDTO(accessToken, refreshToken);

    }

    public TokensDTO convertTokensDTO(AuthenticationUserRequestDTO authenticationUserRequestDTO) {
        return new TokensDTO(authenticationUserRequestDTO.accessToken(), authenticationUserRequestDTO.refreshToken());
    }

    public CsrfTokenDTO convertCsrfTokenDTO(Long userId, String csrfToken) {
        return new CsrfTokenDTO(userId, csrfToken);
    }

    public ValidateRefreshTokenDTO convertToValidationRefreshTokenDTO(Long userId, TokensDTO TokensDTO) {
        return new ValidateRefreshTokenDTO(userId, TokensDTO.refreshToken());
}
    public AuthUserEntity convertAuthEntityConvert(FullUserCreateRequestDTO fullUserCreateRequestDTO, CustomPasswordEncoder customPasswordEncoder) {
        return AuthUserEntity.builder()
                .email(fullUserCreateRequestDTO.email())
                .password(customPasswordEncoder.encode(fullUserCreateRequestDTO.password()))
                .build();
    }


    public UserCreateRequestDTO convertUserCreateRequestDTO(FullUserCreateRequestDTO fullUserCreateRequestDTO, Long userId) {
        return new UserCreateRequestDTO(
                userId,     // User Id
                fullUserCreateRequestDTO.name(),    // Name
                fullUserCreateRequestDTO.address(), // Address
                fullUserCreateRequestDTO.birth(),   // Birth
                fullUserCreateRequestDTO.phone()   // Phone
        );
    }

    public LoginSuccessDTO convertLoginSuccessDTO(TokensDTO tokensDTO, AuthUserEntity authUserEntity, String csrfToken) {
        LoginResponseDTO loginResponseDTO = new LoginResponseDTO(
                authUserEntity.getEmail(), // email
                getRole(authUserEntity.getAuthorities())  // Role
        );

        return new LoginSuccessDTO(tokensDTO, loginResponseDTO, csrfToken);
    }
    public String convertAuthoritiesJson(AuthUserEntity authUserEntity) {
        List<Authority> authorities = convertAuthorities(authUserEntity.getAuthorities());
        try {
            return objectMapper.writeValueAsString(authorities);
        } catch (JsonProcessingException e) {
            throw  new RuntimeException("Error Converting authorities to Json", e);
        }
    }

    private String getJwt(Cookie[] cookies) {
        Optional<Cookie> jwtCookie = Arrays.stream(cookies).filter(cookie ->
                cookie.getName().equals(TokenEnum.ACCESS_TOKEN.getName())
        )
                .findFirst();
        return jwtCookie.orElseThrow(PermissionDeniedException::new).getValue();
    }

    private String getRefreshToken(Cookie[] cookies) {
        Optional<Cookie> refreshTokenCookie = Arrays.stream(cookies).filter(cookie ->
                cookie.getName().equals(TokenEnum.REFRESH_TOKEN.getName()))
                .findFirst();

        return refreshTokenCookie.map(Cookie::getValue).orElse("");
    }

    private List<Authority> convertAuthorities(List<AuthorityEntity> list) {
        return list.stream()
                .map(authorityEntity -> Authority.builder()
                        .authority(authorityEntity.getName())
                        .build())
                .toList();
    }

    // 나중에 수정해야 함 => 유저에 대한 검증 로직이 전부 통과 했지만, Role이 없다? 단순히 예외로 처리할 문제가 아님
    private String getRole(List<AuthorityEntity> list) {
        return list.stream()
                .map(AuthorityEntity::getName)
                .filter(name -> name.startsWith("ROLE_"))
                .findFirst()
                .orElseGet(() -> logging(list.get(0).getId()));
    }
    private String logging(Long userId) {
        log.error("Having id {} User Validation is OK. But, User Role is Not Found!! Critical Issue!", userId);
        // 임시 방편으로 USER로 찍어서 보내주기
        return "ROLE_USER";
    }
}
