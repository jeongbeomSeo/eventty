package com.eventty.authservice.applicaiton.service.subservices;

import com.eventty.authservice.applicaiton.dto.CsrfTokenDTO;
import com.eventty.authservice.applicaiton.dto.TokenParsingDTO;
import com.eventty.authservice.applicaiton.dto.TokensDTO;
import com.eventty.authservice.applicaiton.service.utils.CustomPasswordEncoder;
import com.eventty.authservice.applicaiton.service.utils.token.TokenProvider;
import com.eventty.authservice.domain.entity.AuthUserEntity;
import com.eventty.authservice.domain.exception.InvalidCsrfTokenException;
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
    public void csrfTokenValidationCheck(CsrfTokenDTO csrfTokenDTO) {
        String existedCsrfToken = tokenProvider.getCsrfToken(csrfTokenDTO.userId());

        if (!csrfTokenDTO.value().equals(existedCsrfToken))
            throw new InvalidCsrfTokenException(csrfTokenDTO);
    }

    @Override
    public String getNewCsrfToken(CsrfTokenDTO csrfTokenDTO) {
        if (csrfTokenDTO.value().isEmpty())
            return tokenProvider.saveCsrfToken(csrfTokenDTO.userId());
        else
            return tokenProvider.updateCsrfToken(csrfTokenDTO.userId());
    }

    @Override
    public TokenParsingDTO getTokenParsingDTO(AuthenticationUserRequestDTO authenticationUserRequestDTO) {
        return tokenProvider.parsingToken(authenticationUserRequestDTO);
    }

    @Override
    public boolean credentialMatch(UserLoginRequestDTO userLoginRequestDTO, AuthUserEntity authUserEntity, CustomPasswordEncoder passwordEncoder) {
        if (!passwordEncoder.match(userLoginRequestDTO.getPassword(), authUserEntity.getPassword())) {
            throw new InvalidPasswordException(userLoginRequestDTO);
        }

        return true;
    }

    // 검증 로직 X
    @Override
    public TokensDTO getToken(AuthUserEntity authUserEntity) {
        // 2시간 동안 유효한 액세스 토큰 생성 및 2일 동안 유효한 리프레시 토큰 생성 
        return tokenProvider.getAllToken(authUserEntity);
    }

    // 검증 로직 O
    @Override
    public TokensDTO getNewTokens(AuthUserEntity authUserEntity, GetNewTokensRequestDTO getNewTokensRequestDTO) {

        tokenProvider.refreshTokenValidationCheck(getNewTokensRequestDTO);

        return getToken(authUserEntity);
    }
}
