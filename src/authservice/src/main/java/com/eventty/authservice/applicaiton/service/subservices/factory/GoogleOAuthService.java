package com.eventty.authservice.applicaiton.service.subservices.factory;

import com.eventty.authservice.applicaiton.dto.OAuthUserInfoDTO;
import com.eventty.authservice.domain.Enum.OAuth;
import com.eventty.authservice.domain.entity.OAuthUserEntity;
import com.eventty.authservice.domain.exception.OAuthNotFoundUserInfoException;
import com.eventty.authservice.domain.exception.OAuthNotFoundVerifiedEmailException;
import com.eventty.authservice.domain.repository.OAuthUserRepository;
import com.eventty.authservice.presentation.dto.request.OAuthLoginRequestDTO;
import com.eventty.authservice.presentation.dto.response.GoogleUserInfoResponseDTO;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Slf4j
@Service("GoogleOAuthService")
public class GoogleOAuthService implements OAuthService{

    private final RestTemplate restTemplate;
    private final OAuthUserRepository oAuthUserRepository;
    private EntityManager em;


    @Autowired
    public GoogleOAuthService(RestTemplate restTemplate, OAuthUserRepository oAuthUserRepository) {
        this.restTemplate = restTemplate;
        this.oAuthUserRepository = oAuthUserRepository;
    }

    public OAuthUserInfoDTO getUserInfo(OAuthLoginRequestDTO oAuthLoginRequestDTO) {

        HttpEntity<Void> entity = createOAthHttpEntity(oAuthLoginRequestDTO);

        log.debug("Auth Server", "Google Resource Server", "Request goolge user info");

        ResponseEntity<GoogleUserInfoResponseDTO> response = restTemplate.exchange(
                OAuth.GOOGLE.getUri(), OAuth.GOOGLE.getMethod(), entity, GoogleUserInfoResponseDTO.class
        );

        if (response.getBody() == null)
            throw new OAuthNotFoundUserInfoException(OAuth.GOOGLE.getSocialName());

        if (!response.getBody().getVerified_email())
            throw new OAuthNotFoundVerifiedEmailException(OAuth.GOOGLE.getSocialName());

        log.debug("Api Call 성공:: Client ID: {}", response.getBody().getId());

        return new OAuthUserInfoDTO(
                response.getBody().getId(),
                response.getBody().getEmail(),
                response.getBody().getName(),
                null,
                null,
                response.getBody().getPricture());
    }

    @Override
    public Optional<OAuthUserEntity> findOAuthUserEntity(String clientId) {
        // 예외 발생 X
        return oAuthUserRepository.findOAuthUserEntityBySocialNameAndClientId(OAuth.GOOGLE.getSocialName(), clientId);
    }

    @Override
    public Long create(OAuthUserEntity oAuthUserEntity) {

        em.persist(oAuthUserEntity);

        return oAuthUserEntity.getId();
    }

    private HttpEntity<Void> createOAthHttpEntity(OAuthLoginRequestDTO oAuthLoginRequestDTO) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", oAuthLoginRequestDTO.getTokenType() + " " + oAuthLoginRequestDTO.getAccessToken());

        return new HttpEntity<>(null, headers);
    }
}
