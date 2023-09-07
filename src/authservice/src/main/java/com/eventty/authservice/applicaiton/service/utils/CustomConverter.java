package com.eventty.authservice.applicaiton.service.utils;

import com.eventty.authservice.api.dto.UserCreateRequestDTO;
import com.eventty.authservice.domain.entity.AuthUserEntity;
import com.eventty.authservice.presentation.dto.FullUserCreateRequestDTO;
import org.springframework.stereotype.Component;

@Component
public class CustomConverter {

    public AuthUserEntity userDTOToAuthEntityConvert(FullUserCreateRequestDTO fullUserCreateRequestDTO, CustomPasswordEncoder customPasswordEncoder) {
        return AuthUserEntity.builder()
                .email(fullUserCreateRequestDTO.getEmail())
                .password(customPasswordEncoder.encode(fullUserCreateRequestDTO.getPassword()))
                .build();
    }


    public UserCreateRequestDTO fullUserDTOToUserDTOConvert(FullUserCreateRequestDTO fullUserCreateRequestDTO, Long userId) {
        return UserCreateRequestDTO.builder()
                .userId(userId)
                .name(fullUserCreateRequestDTO.getName())
                .address(fullUserCreateRequestDTO.getAddress())
                .birth(fullUserCreateRequestDTO.getBirth())
                .phone(fullUserCreateRequestDTO.getPhone())
                .build();
    }
}
