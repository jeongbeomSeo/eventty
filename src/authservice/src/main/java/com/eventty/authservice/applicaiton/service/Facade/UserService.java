package com.eventty.authservice.applicaiton.service.Facade;

import com.eventty.authservice.applicaiton.dto.CsrfTokenDTO;
import com.eventty.authservice.applicaiton.dto.LoginSuccessDTO;
import com.eventty.authservice.domain.Enum.UserRole;
import com.eventty.authservice.presentation.dto.request.AuthenticateUserRequestDTO;
import com.eventty.authservice.presentation.dto.request.ChangePWRequestDTO;
import com.eventty.authservice.presentation.dto.request.FullUserCreateRequestDTO;
import com.eventty.authservice.presentation.dto.request.UserLoginRequestDTO;
import com.eventty.authservice.presentation.dto.response.AuthenticationDetailsResponseDTO;
import jakarta.servlet.http.Cookie;

public interface UserService {

    Long createUser(FullUserCreateRequestDTO fullUserCreateRequestDTO, UserRole userRole);
    void validateEmailNotDuplicated(String email);
    LoginSuccessDTO login(UserLoginRequestDTO userLoginRequestDTO);
    AuthenticationDetailsResponseDTO authenticateUser(AuthenticateUserRequestDTO authenticateUserRequestDTO);
    Long deleteUser(Cookie[] cookies, String csrfToken);
    Long logout(Cookie[] cookies, String csrfToken);
    CsrfTokenDTO changePW(ChangePWRequestDTO changePWRequestDTO, Cookie[] cookies, String csrfToken);
}
