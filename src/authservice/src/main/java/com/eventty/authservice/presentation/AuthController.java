package com.eventty.authservice.presentation;

import com.eventty.authservice.applicaiton.dto.TokenDTO;
import com.eventty.authservice.infrastructure.annotation.Auth;
import com.eventty.authservice.infrastructure.utils.CookieCreator;
import com.eventty.authservice.presentation.dto.UserLoginRequestDTO;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import io.swagger.v3.oas.annotations.tags.Tag;

import com.eventty.authservice.presentation.dto.FullUserCreateRequestDTO;
import com.eventty.authservice.applicaiton.service.Facade.UserService;
import com.eventty.authservice.domain.Enum.UserRole;
import com.eventty.authservice.presentation.dto.IsUserDuplicateRequestDTO;
import com.eventty.authservice.common.Enum.SuccessCode;

import javax.management.relation.Role;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Tag(name= "Auth", description = "Auth API")
public class AuthController {

    private final UserService userService;

    /**
     * 회원가입
     */
    @PostMapping("/me/{userRole}")
    public ResponseEntity<Void> createUser(@Valid @RequestBody FullUserCreateRequestDTO userCreateRequestDTO,
                                           @PathVariable("userRole") UserRole userRole) {

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
    public ResponseEntity<Void> login(@Valid @RequestBody UserLoginRequestDTO userLoginRequestDTO) {

        // JWT & Refresh Token
        TokenDTO tokenDTO = userService.login(userLoginRequestDTO);
        ResponseCookie responseAccessTokenCookie = CookieCreator.createAccessTokenCookie(tokenDTO.getAccessToken());
        ResponseCookie responseRefreshTokenCookie = CookieCreator.createRefreshTokenCookie(tokenDTO.getRefreshToken());

        // Combine the two cookies into a single header value
        String combinedCookies = responseAccessTokenCookie.toString() + "; " + responseRefreshTokenCookie.toString();

        return ResponseEntity
                .status(SuccessCode.IS_OK.getStatus())
                .header(HttpHeaders.SET_COOKIE, combinedCookies)
                .body(null);
    }

    /**
     * 로그아웃
     */
    @Auth(Roles = {UserRole.HOST})
    @DeleteMapping("/me")
    public ResponseEntity<Void> logout(@AuthenticationPrincipal Authentication authentication,
                                       HttpServletRequest request) {

        Cookie[] cookies = request.getCookies();


        return null;
    }

}
