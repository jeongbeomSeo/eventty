package com.eventty.authservice.presentation;

import com.eventty.authservice.applicaiton.dto.CsrfTokenDTO;
import com.eventty.authservice.applicaiton.dto.LoginSuccessDTO;
import com.eventty.authservice.applicaiton.service.Facade.UserService;
import com.eventty.authservice.domain.Enum.UserRole;
import com.eventty.authservice.global.Enum.SuccessCode;
import com.eventty.authservice.global.response.SuccessResponseDTO;
import com.eventty.authservice.infrastructure.utils.CookieCreator;
import com.eventty.authservice.presentation.dto.request.*;
import com.eventty.authservice.presentation.dto.response.AuthenticationDetailsResponseDTO;
import com.eventty.authservice.presentation.dto.response.LoginResponseDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

        // 유저 생성
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

        // 이메일 검증
        userService.validateEmailNotDuplicated(isUserDuplicateRequestDTO.email());

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

        // 로그인
        LoginSuccessDTO loginSuccessDTO = userService.login(userLoginRequestDTO);

        // JWT & Refresh Token
        ResponseCookie jwtCookie = CookieCreator.createAccessTokenCookie(
                loginSuccessDTO.tokensDTO().accessToken());

        ResponseCookie refreshTokenCookie = CookieCreator.createRefreshTokenCookie(
                loginSuccessDTO.tokensDTO().refreshToken());

        // Response
        LoginResponseDTO loginResponseDTO = loginSuccessDTO.loginResponseDTO();

        return ResponseEntity
                .status(SuccessCode.IS_OK.getStatus())
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
                .header(HEADER_CSRF, loginSuccessDTO.csrfToken())
                .body(SuccessResponseDTO.of(loginResponseDTO));
    }

    /**
     * 회원 탈퇴(Soft Delete)
     */
    @DeleteMapping("/me")
    // @Permission(Roles = {UserRole.USER})
    public ResponseEntity<Void> delete(HttpServletRequest request) {
        log.debug("Current Position: Controller :: 회원 탈퇴");

        // Cookie에는 JWT, CSRF가 담겨오고, Header에는 CSRF가 담겨온다.
        Cookie[] cookies = request.getCookies();
        String csrfToken = request.getHeader(HEADER_CSRF);

        // 유저 삭제
        userService.deleteUser(cookies, csrfToken);

        // 사용자의 브라우저에 저장되어 있는 토큰 삭제
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

        // 회원 검증
        AuthenticationDetailsResponseDTO authenticationDetailsResponseDTO = userService.authenticateUser(authenticationUserRequestDTO);

        return ResponseEntity
                .status(SuccessCode.IS_OK.getStatus())
                .body(SuccessResponseDTO.of(authenticationDetailsResponseDTO));
    }
    /**
     * 유저 로그아웃
     *
     * 이 경우, 인증 서버가 endPoint이므로 게이트웨이에서 Response를 수정할 것으로 고려하는 것이 아닌 직접 Response에 담아서 보내는 방향으로 진행
     * -> 게이트웨이와 인증 서버간의 추가적인 결합이 발생하지 않는다. (즉, 인증 서버가 엔드포인트인 경우 게이트웨이에서 JWT 필터를 걸 필요가 X
     * => 만약에 기존 로직과 동일하게 진행할 경우 최종 Response의 Body에 토큰의 정보가 담기게 되므로 게이트웨이는 이것을 뺴고 헤더에 담아서 보내야되는 추가적인 로직이 발생.
     * 따라서, 결합도가 높아짐
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

    /**
     * 비밀번호 변경
     */
    @PostMapping("/changePW")
    public ResponseEntity<Void> changePW(@Valid @RequestBody ChangePWRequestDTO changePWRequestDTO, HttpServletRequest request) {
        log.debug("Current Position: Controller:: 회원 비밀번호 변경");

        Cookie[] cookies = request.getCookies();
        String csrfToken = request.getHeader(HEADER_CSRF);

        CsrfTokenDTO csrfTokenDTO = userService.changePW(changePWRequestDTO,cookies, csrfToken);

        return ResponseEntity
                .status(SuccessCode.IS_OK.getStatus())
                .header(HEADER_CSRF, csrfTokenDTO.value())
                .body(null);
    }
}
