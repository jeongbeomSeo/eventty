package com.eventty.authservice.presentation;

import com.eventty.authservice.applicaiton.dto.LoginSuccessDTO;
import com.eventty.authservice.global.response.SuccessResponseDTO;
import com.eventty.authservice.infrastructure.utils.CookieCreator;
import com.eventty.authservice.presentation.dto.request.*;
import com.eventty.authservice.presentation.dto.response.AuthenticationDetailsResponseDTO;
import com.eventty.authservice.presentation.dto.response.LoginResponseDTO;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import io.swagger.v3.oas.annotations.tags.Tag;

import com.eventty.authservice.applicaiton.service.Facade.UserService;
import com.eventty.authservice.domain.Enum.UserRole;
import com.eventty.authservice.global.Enum.SuccessCode;

@RestController
@RequiredArgsConstructor
@RequestMapping("")
@Slf4j
@Tag(name= "Auth", description = "Auth API")
public class AuthController {

    private final String HEADER_CSRF = "X-CSRF-TOKEN";

    private final UserService userService;

    /**
     * 회원가입
     */
    @PostMapping("/me/{userRole}")
    public ResponseEntity<Void> createUser(@Valid @RequestBody FullUserCreateRequestDTO userCreateRequestDTO,
                                           @PathVariable("userRole") UserRole userRole) {
        log.debug("Current Position: Controller :: 회원가입");


        userService.createUser(userCreateRequestDTO, userRole);

        return ResponseEntity
                .status(SuccessCode.USER_CREATED.getStatus())
                .body(null);
    }

    /**
     * 이메일 검증
     */
    @PostMapping("/email")
    public ResponseEntity<Void> isDuplicateEmail(@Valid @RequestBody IsUserDuplicateRequestDTO isUserDuplicateRequestDTO) {
        log.debug("Current Position: Controller :: 이메일 검증");

        userService.validateEmailNotDuplicated(isUserDuplicateRequestDTO.getEmail());

        return ResponseEntity
                .status(SuccessCode.IS_OK.getStatus())
                .body(null);
    }

    /**
     * 로그인
     *
     * HttpServletResponse httpServletResponse를 사용해서 쿠키에 담아도 되지만,
     * ResponseEntity만 사용하여 응답의 명시성을 높이는 방향으로 진행했습니다.
     */
    @PostMapping("/login")
    public ResponseEntity<SuccessResponseDTO<LoginResponseDTO>> login(@Valid @RequestBody UserLoginRequestDTO userLoginRequestDTO) {
        log.debug("Current Position: Controller :: 로그인");

        // JWT & Refresh Token
        LoginSuccessDTO loginSuccessDTO = userService.login(userLoginRequestDTO);

        ResponseCookie jwtCookie = CookieCreator.createAccessTokenCookie(
                loginSuccessDTO.tokensDTO().accessToken());

        ResponseCookie refreshTokenCookie = CookieCreator.createRefreshTokenCookie(
                loginSuccessDTO.tokensDTO().refreshToken());

        return ResponseEntity
                .status(SuccessCode.IS_OK.getStatus())
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
                .body(SuccessResponseDTO.of(loginSuccessDTO.loginResponseDTO()));
    }

    /**
     * 회원 탈퇴(Soft Delete)
     */
    @DeleteMapping("/me")
    // @Permission(Roles = {UserRole.USER})
    public ResponseEntity<Void> delete(HttpServletRequest request) {
        log.debug("Current Position: Controller :: 회원 탈퇴");

        Cookie[] cookies = request.getCookies();
        String csrfToken = request.getHeader(HEADER_CSRF);
        userService.deleteUser(cookies, csrfToken);

        ResponseCookie deleteAccessToken = CookieCreator.deleteAccessTokenCookie();
        ResponseCookie deleteRefreshToken = CookieCreator.deleteRefreshTokenCookie();

        return ResponseEntity
                .status(SuccessCode.IS_OK.getStatus())
                .header(HttpHeaders.SET_COOKIE, deleteAccessToken.toString())
                .header(HttpHeaders.SET_COOKIE, deleteRefreshToken.toString())
                .body(null);
    }

    /**
     * 유저에 대한 검증
     */
    @PostMapping("/api/authenticate/user")
    public ResponseEntity<SuccessResponseDTO<AuthenticationDetailsResponseDTO>> authenticataeUser(AuthenticationUserRequestDTO authenticationUserRequestDTO) {
        log.debug("Current Position: Controller :: 회원 검증");

        AuthenticationDetailsResponseDTO authenticationDetailsResponseDTO = userService.authenticateUser(authenticationUserRequestDTO);

        return ResponseEntity
                .status(SuccessCode.IS_OK.getStatus())
                .body(SuccessResponseDTO.of(authenticationDetailsResponseDTO));
    }

    /**
     * 유저 로그아웃
     */
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        log.debug("Current Position: Controller :: 회원 로그아웃");

        Cookie[] cookies = request.getCookies();
        String csrfToken = request.getHeader(HEADER_CSRF);

        userService.logout(cookies, csrfToken);

        ResponseCookie deleteAccessToken = CookieCreator.deleteAccessTokenCookie();
        ResponseCookie deleteRefreshToken = CookieCreator.deleteRefreshTokenCookie();

        return ResponseEntity
                .status(SuccessCode.IS_OK.getStatus())
                .header(HttpHeaders.SET_COOKIE, deleteAccessToken.toString())
                .header(HttpHeaders.SET_COOKIE, deleteRefreshToken.toString())
                .body(null);
    }
}
