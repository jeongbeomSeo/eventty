package com.eventty.authservice.applicaiton.service.utils;

import com.eventty.authservice.api.dto.UserCreateRequestDTO;
import com.eventty.authservice.domain.entity.AuthUserEntity;
import com.eventty.authservice.presentation.dto.FullUserCreateRequestDTO;

public interface CustomConverter {
    AuthUserEntity userDTOToAuthEntityConvert(FullUserCreateRequestDTO fullUserCreateRequestDTO, CustomPasswordEncoder customPasswordEncoder);

    UserCreateRequestDTO fullUserDTOToUserDTOConvert(FullUserCreateRequestDTO fullUserCreateRequestDTO, Long authUserId);
}
