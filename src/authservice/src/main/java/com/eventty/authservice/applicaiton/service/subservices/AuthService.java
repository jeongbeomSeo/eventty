package com.eventty.authservice.applicaiton.service.subservices;

import com.eventty.authservice.applicaiton.dto.AuthenticationDetailsDTO;
import com.eventty.authservice.applicaiton.dto.TokensDTO;
import com.eventty.authservice.applicaiton.service.utils.CustomPasswordEncoder;
import com.eventty.authservice.domain.entity.AuthUserEntity;
import com.eventty.authservice.presentation.dto.request.AuthenticationUserRequestDTO;
import com.eventty.authservice.presentation.dto.request.GetNewTokensRequestDTO;
import com.eventty.authservice.presentation.dto.request.UserLoginRequestDTO;
import com.eventty.authservice.presentation.dto.response.NewTokensResponseDTO;


public interface AuthService {

    boolean credentialMatch(UserLoginRequestDTO userLoginRequestDTO, AuthUserEntity authUserEntity, CustomPasswordEncoder passwordEncoder);

    TokensDTO getToken(AuthUserEntity authUserEntity);


    TokensDTO getNewTokens(AuthUserEntity authUserEntity, GetNewTokensRequestDTO getNewTokensRequestDTO);
    AuthenticationDetailsDTO authticateTokens(AuthenticationUserRequestDTO authenticationUserRequestDTO);
}
