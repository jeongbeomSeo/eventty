package com.eventty.authservice.presentation;

import com.eventty.authservice.applicaiton.dto.LoginSuccessDTO;
import com.eventty.authservice.global.response.SuccessResponseDTO;
import com.eventty.authservice.infrastructure.annotation.Permission;
import com.eventty.authservice.infrastructure.resolver.LoginUser;
import com.eventty.authservice.infrastructure.utils.CookieCreator;
import com.eventty.authservice.presentation.dto.request.GetNewTokensRequestDTO;
import com.eventty.authservice.presentation.dto.request.UserLoginRequestDTO;
import com.eventty.authservice.presentation.dto.response.LoginResponseDTO;
import com.eventty.authservice.presentation.dto.response.NewTokensResponseDTO;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import io.swagger.v3.oas.annotations.tags.Tag;

import com.eventty.authservice.presentation.dto.request.FullUserCreateRequestDTO;
import com.eventty.authservice.applicaiton.service.Facade.UserService;
import com.eventty.authservice.domain.Enum.UserRole;
import com.eventty.authservice.presentation.dto.request.IsUserDuplicateRequestDTO;
import com.eventty.authservice.global.Enum.SuccessCode;

@RestController
@RequiredArgsConstructor
@RequestMapping("")
@Slf4j
@Tag(name= "Auth", description = "Auth API")
public class AuthController {

    private final UserService userService;

    /**
     * 회원가입
     */
    @PostMapping("/me/{userRole}")
    public ResponseEntity<Void> createUser(@Valid @RequestBody FullUserCreateRequestDTO userCreateRequestDTO,
                                           @PathVariable("userRole") UserRole userRole) {
        log.info("Current Position: Controller :: 회원가입");


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
        log.info("Current Position: Controller :: 이메일 검증");

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
        log.info("Current Position: Controller :: 로그인");

        // JWT & Refresh Token
        LoginSuccessDTO loginSuccessDTO = userService.login(userLoginRequestDTO);

        ResponseCookie jwtCookie = CookieCreator.createAccessTokenCookie(
                loginSuccessDTO.getTokensDTO().getAccessToken());

        ResponseCookie refreshTokenCookie = CookieCreator.createRefreshTokenCookie(
                loginSuccessDTO.getTokensDTO().getRefreshToken());

        return ResponseEntity
                .status(SuccessCode.IS_OK.getStatus())
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
                .body(SuccessResponseDTO.of(loginSuccessDTO.getLoginResponseDTO()));
    }

    /**
     * 회원 탈퇴(Soft Delete)
     */
    @DeleteMapping("/me")
    // @Permission(Roles = {UserRole.USER})
    public ResponseEntity<Void> delete(LoginUser loginUser, HttpServletResponse response) {
        log.info("Current Position: Controller :: 회원 탈퇴");

        Long userId = loginUser.getUserId();
        userService.deleteUser(userId);

        Cookie deleteAccessToken = CookieCreator.deleteAccessTokenCookie();
        Cookie deleteRefreshToken = CookieCreator.deleteRefreshTokenCoolie();

        response.addCookie(deleteAccessToken);
        response.addCookie(deleteRefreshToken);

        return ResponseEntity
                .status(SuccessCode.IS_OK.getStatus())
                .body(null);
    }

    /**
     * Refresh Token 검증 및 새로운 Tokens 발급
     */
    @PostMapping("/api/newtokens")
    public ResponseEntity<SuccessResponseDTO<NewTokensResponseDTO>> getNewTokens(@RequestBody GetNewTokensRequestDTO getNewTokensRequestDTO) {
        log.info("Current Position: Controller :: 새로운 Token 발급");

        NewTokensResponseDTO newTokensResponseDTO = userService.getNewTokens(getNewTokensRequestDTO);

        return ResponseEntity
                .status(SuccessCode.IS_OK.getStatus())
                .body(SuccessResponseDTO.of(newTokensResponseDTO));
    }
}
