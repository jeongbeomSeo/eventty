package com.eventty.authservice.applicaiton.service.subservices.factory;

import com.eventty.authservice.applicaiton.dto.OAuthUserInfoDTO;
import com.eventty.authservice.domain.Enum.OAuth;
import com.eventty.authservice.domain.entity.OAuthUserEntity;
import com.eventty.authservice.domain.exception.OAuthNotFoundUserInfoException;
import com.eventty.authservice.domain.exception.OAuthNotFoundVerifiedEmailException;
import com.eventty.authservice.domain.repository.OAuthUserRepository;
import com.eventty.authservice.presentation.dto.request.OAuthLoginRequestDTO;
import com.eventty.authservice.presentation.dto.response.NaverUserInfoResponseDTO;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Slf4j
@Service("naverOAuthService")
public class NaverOAuthService implements OAuthService {

    private final RestTemplate restTemplate;
    private final OAuthUserRepository oAuthUserRepository;
    private final EntityManager em;

    @Autowired
    public NaverOAuthService(RestTemplate restTemplate, OAuthUserRepository oAuthUserRepository, EntityManager em) {
        this.restTemplate = restTemplate;
        this.oAuthUserRepository = oAuthUserRepository;
        this.em = em;
    }


    @Override
    public OAuthUserInfoDTO getUserInfo(OAuthLoginRequestDTO oAuthLoginRequestDTO) {

        HttpEntity<Void> entity = createOAthHttpEntity(oAuthLoginRequestDTO);

        log.info("API 호출 From: {} To: {} Purpose: {}",
                "Auth Server", "Naver Resource Server", "Request Naver user info");

        ResponseEntity<NaverUserInfoResponseDTO> response = restTemplate.exchange(
                OAuth.NAVER.getUri(), OAuth.NAVER.getMethod(), entity, NaverUserInfoResponseDTO.class
        );

        if (response.getBody() == null)
            throw new OAuthNotFoundUserInfoException(OAuth.NAVER.getSocialName());
        if (response.getBody() == null)
            throw new OAuthNotFoundVerifiedEmailException(OAuth.NAVER.getSocialName());

        log.debug("Api Call 성공:: Client ID: {}", response.getBody().getId());

        return new OAuthUserInfoDTO(
                response.getBody().getId(),
                response.getBody().getEmail(),
                response.getBody().getName(),
                convertLocaleDate(response.getBody().getBrithyear(), response.getBody().getBirthday()),
                response.getBody().getMobile(),
                response.getBody().getProfileImage()
        );
    }

    @Override
    public Optional<OAuthUserEntity> findOAuthUserEntity(String clientId) {
        // 예외 발생 X
        return oAuthUserRepository.findOAuthUserEntityBySocialNameAndClientId(OAuth.NAVER.getSocialName(), clientId);
    }

    @Override
    public Long create(OAuthUserEntity oAuthUserEntity) {

        em.persist(oAuthUserEntity);

        return oAuthUserEntity.getId();
    }

    private HttpEntity<Void> createOAthHttpEntity(OAuthLoginRequestDTO oAuthLoginRequestDTO) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", oAuthLoginRequestDTO.getTokenType() + " " + oAuthLoginRequestDTO.getAccessToken());

        return new HttpEntity<>(headers);
    }

    private LocalDate convertLocaleDate(String birthyear, String birthday) {

        if (birthyear == null || birthday == null)
            return null;

        // 문자열 합치기
        String fullBirthDate = birthyear + "-" + birthday;

        // 문자열을 LocalDate로 파싱
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(fullBirthDate, formatter);
    }
}
