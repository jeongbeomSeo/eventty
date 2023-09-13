package com.eventty.authservice.applicaiton.service.utils;

import com.eventty.authservice.api.dto.UserCreateRequestDTO;
import com.eventty.authservice.applicaiton.dto.LoginSuccessDTO;
import com.eventty.authservice.applicaiton.dto.TokensDTO;
import com.eventty.authservice.applicaiton.service.Facade.UserService;
import com.eventty.authservice.applicaiton.service.subservices.AuthService;
import com.eventty.authservice.domain.entity.AuthUserEntity;
import com.eventty.authservice.domain.entity.AuthorityEntity;
import com.eventty.authservice.presentation.dto.request.FullUserCreateRequestDTO;
import com.eventty.authservice.presentation.dto.response.LoginResponseDTO;
import com.eventty.authservice.presentation.dto.response.NewTokensResponseDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomConverter {

    public AuthUserEntity userDTOToAuthEntityConvert(FullUserCreateRequestDTO fullUserCreateRequestDTO, CustomPasswordEncoder customPasswordEncoder) {
        return AuthUserEntity.builder()
                .email(fullUserCreateRequestDTO.getEmail())
                .password(customPasswordEncoder.encode(fullUserCreateRequestDTO.getPassword()))
                .build();
    }


    public UserCreateRequestDTO fullUserDTOToUserDTO(FullUserCreateRequestDTO fullUserCreateRequestDTO, Long userId) {
        return UserCreateRequestDTO.builder()
                .userId(userId)
                .name(fullUserCreateRequestDTO.getName())
                .address(fullUserCreateRequestDTO.getAddress())
                .birth(fullUserCreateRequestDTO.getBirth())
                .phone(fullUserCreateRequestDTO.getPhone())
                .build();
    }

    public LoginSuccessDTO authUserEntityTologinSuccessDTO(AuthService authService, AuthUserEntity authUserEntity) {
        TokensDTO token = authService.getToken(authUserEntity);
        LoginResponseDTO loginResponseDTO = LoginResponseDTO.builder()
                .email(authUserEntity.getEmail())
                .authoritiesNameList(convertAuthorities(authUserEntity.getAuthorities()))
                .build();

        return LoginSuccessDTO.builder()
                .tokensDTO(token)
                .loginResponseDTO(loginResponseDTO)
                .build();
    }


    public NewTokensResponseDTO tokensDTOToNewTokensResponseDTO(TokensDTO tokensDTO) {
        return NewTokensResponseDTO.builder()
                .accessToken(tokensDTO.getAccessToken())
                .refreshToken(tokensDTO.getRefreshToken())
                .build();
    }

    private static List<String> convertAuthorities(List<AuthorityEntity> list) {
        return list.stream()
                .map(AuthorityEntity::getName)
                .toList();
    }
}
