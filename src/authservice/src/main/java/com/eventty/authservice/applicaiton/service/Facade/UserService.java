package com.eventty.authservice.applicaiton.service.Facade;

import com.eventty.authservice.applicaiton.dto.CsrfTokenDTO;
import com.eventty.authservice.applicaiton.dto.LoginSuccessDTO;
import com.eventty.authservice.applicaiton.dto.SessionTokensDTO;
import com.eventty.authservice.domain.Enum.UserRole;
import com.eventty.authservice.presentation.dto.request.*;
import com.eventty.authservice.presentation.dto.response.AuthenticationDetailsResponseDTO;

public interface UserService {

    Long createUser(FullUserCreateRequestDTO fullUserCreateRequestDTO, UserRole userRole);
    void validateEmailNotDuplicated(String email);
    LoginSuccessDTO login(UserLoginRequestDTO userLoginRequestDTO);
    AuthenticationDetailsResponseDTO authenticateUser(AuthenticateUserRequestDTO authenticateUserRequestDTO);
    Long deleteUser(SessionTokensDTO sessionTokensDTO, String csrfToken);
    Long logout(SessionTokensDTO sessionTokensDTO, String csrfToken);
    CsrfTokenDTO changePW(ChangePWRequestDTO changePWRequestDTO, SessionTokensDTO sessionTokensDTO, String csrfToken);
    String queryFindEmail(FindEmailRequestDTO findEmailRequestDTO);
    String queryFindPW(FindPWRequestDTO findPWRequestDTO);
}
