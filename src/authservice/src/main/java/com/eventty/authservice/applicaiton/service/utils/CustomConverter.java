package com.eventty.authservice.applicaiton.service.utils;

import com.eventty.authservice.api.dto.UserCreateRequestDTO;
import com.eventty.authservice.applicaiton.dto.LoginSuccessDTO;
import com.eventty.authservice.applicaiton.dto.TokensDTO;
import com.eventty.authservice.applicaiton.service.subservices.AuthService;
import com.eventty.authservice.domain.entity.AuthUserEntity;
import com.eventty.authservice.domain.entity.AuthorityEntity;
import com.eventty.authservice.presentation.dto.request.FullUserCreateRequestDTO;
import com.eventty.authservice.presentation.dto.response.LoginResponseDTO;
import com.eventty.authservice.presentation.dto.response.NewTokensResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
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
                // .authoritiesNameList(convertAuthorities(authUserEntity.getAuthorities()))
                .role(getRole(authUserEntity.getAuthorities()))
                // .csrfToken()
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

    // 나중에 수정해야 함 => 유저에 대한 검증 로직이 전부 통과 했지만, Role이 없다? 단순히 예외로 처리할 문제가 아님
    private static String getRole(List<AuthorityEntity> list) {
        return list.stream()
                .map(AuthorityEntity::getName)
                .filter(name -> name.startsWith("ROLE_"))
                .findFirst()
                .orElseGet( () -> logging(list.get(0).getId()));
    }
    private static String logging(Long userId) {
        log.error("Having id {} User Validation is OK. But, User Role is Not Found!! Critical Issue!", userId);
        // 임시 방편으로 USER로 찍어서 보내주기
        return "ROLE_USER";
    }
}
