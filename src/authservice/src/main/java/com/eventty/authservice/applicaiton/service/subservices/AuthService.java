package com.eventty.authservice.applicaiton.service.subservices;

import com.eventty.authservice.applicaiton.dto.AuthenticationResultDTO;
import com.eventty.authservice.applicaiton.dto.CsrfTokenDTO;
import com.eventty.authservice.applicaiton.dto.TokenParsingDTO;
import com.eventty.authservice.applicaiton.dto.TokensDTO;
import com.eventty.authservice.applicaiton.service.utils.CustomConverter;
import com.eventty.authservice.applicaiton.service.utils.CustomPasswordEncoder;
import com.eventty.authservice.domain.entity.AuthUserEntity;
import com.eventty.authservice.presentation.dto.request.UserLoginRequestDTO;

public interface AuthService {

    AuthenticationResultDTO authenticate(TokensDTO tokensDTO, String csrfToken, CustomConverter converter, UserDetailService userDetailService);
    boolean credentialMatch(UserLoginRequestDTO userLoginRequestDTO, AuthUserEntity authUserEntity, CustomPasswordEncoder passwordEncoder);

    TokensDTO getToken(AuthUserEntity authUserEntity);
    TokenParsingDTO getTokenParsingDTO(TokensDTO tokensDTO);
    void csrfTokenValidationCheck(CsrfTokenDTO csrfTokenDTO);
    String getUpdateCsrfToken(Long userId);
    String getNewCsrfToken(Long userId);
    boolean checkCsrfToken(Long userId);
    void deleteAllToken(Long userId);
}
