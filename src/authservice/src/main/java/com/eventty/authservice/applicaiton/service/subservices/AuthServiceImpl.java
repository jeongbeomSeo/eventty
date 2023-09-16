package com.eventty.authservice.applicaiton.service.subservices;

import com.eventty.authservice.applicaiton.dto.AuthenticationDetailsDTO;
import com.eventty.authservice.applicaiton.dto.TokensDTO;
import com.eventty.authservice.applicaiton.service.utils.CustomPasswordEncoder;
import com.eventty.authservice.applicaiton.service.utils.token.TokenProvider;
import com.eventty.authservice.domain.entity.AuthUserEntity;
import com.eventty.authservice.domain.exception.InvalidPasswordException;
import com.eventty.authservice.presentation.dto.request.AuthenticationUserRequestDTO;
import com.eventty.authservice.presentation.dto.request.GetNewTokensRequestDTO;
import com.eventty.authservice.presentation.dto.request.UserLoginRequestDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final TokenProvider tokenProvider;


    @Override
    public AuthenticationDetailsDTO authticateTokens(AuthenticationUserRequestDTO authenticationUserRequestDTO) {

        AuthenticationDetailsDTO authenticationDetailsDTO = tokenProvider.

        return null;
    }

    @Override
    public boolean credentialMatch(UserLoginRequestDTO userLoginRequestDTO, AuthUserEntity authUserEntity, CustomPasswordEncoder passwordEncoder) {
        if (!passwordEncoder.match(userLoginRequestDTO.getPassword(), authUserEntity.getPassword())) {
            throw new InvalidPasswordException(userLoginRequestDTO);
        }

        return true;
    }

    @Override
    public TokensDTO getToken(AuthUserEntity authUserEntity) {
        // 2시간 동안 유효한 액세스 토큰 생성 및 2일 동안 유효한 리프레시 토큰 생성 
        return tokenProvider.getAllToken(authUserEntity);
    }

    @Override
    public TokensDTO getNewTokens(AuthUserEntity authUserEntity, GetNewTokensRequestDTO getNewTokensRequestDTO) {

        tokenProvider.refreshTokenValidationCheck(getNewTokensRequestDTO);

        return getToken(authUserEntity);
    }
}
