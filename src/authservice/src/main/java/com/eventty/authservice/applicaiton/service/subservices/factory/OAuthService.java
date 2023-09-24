package com.eventty.authservice.applicaiton.service.subservices.factory;

import com.eventty.authservice.applicaiton.dto.OAuthUserInfoDTO;
import com.eventty.authservice.domain.entity.OAuthUserEntity;
import com.eventty.authservice.presentation.dto.request.OAuthLoginRequestDTO;

import java.util.Optional;

public interface OAuthService {
    OAuthUserInfoDTO getUserInfo(OAuthLoginRequestDTO oAuthLoginRequestDTO);
    Optional<OAuthUserEntity> findOAuthUserEntity(String clientId);
    Long create(OAuthUserEntity oAuthUserEntity);
}