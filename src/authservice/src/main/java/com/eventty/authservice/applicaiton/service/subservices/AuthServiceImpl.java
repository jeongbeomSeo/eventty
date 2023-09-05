package com.eventty.authservice.applicaiton.service.subservices;

import com.eventty.authservice.applicaiton.dto.TokenDTO;
import com.eventty.authservice.applicaiton.service.utils.CustomPasswordEncoder;
import com.eventty.authservice.applicaiton.service.utils.token.TokenProvider;
import com.eventty.authservice.domain.entity.AuthUserEntity;
import com.eventty.authservice.domain.exception.InvalidPasswordException;
import com.eventty.authservice.presentation.dto.UserLoginRequestDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final TokenProvider tokenProvider;

    @Override
    public boolean match(UserLoginRequestDTO userLoginRequestDTO, AuthUserEntity authUserEntity, CustomPasswordEncoder passwordEncoder) {
        if (!passwordEncoder.match(userLoginRequestDTO.getPassword(), authUserEntity.getPassword())) {
            throw InvalidPasswordException.EXCEPTION;
        }

        return true;
    }

    @Override
    public TokenDTO getToken(AuthUserEntity authUserEntity) {
        // 2시간 동안 유효한 액세스 토큰 생성 및 2일 동안 유효한 리프레시 토큰 생성 
        return tokenProvider.getAllToken(authUserEntity);
    }
}
