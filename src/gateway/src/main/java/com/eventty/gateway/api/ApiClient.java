package com.eventty.gateway.api;

import com.eventty.gateway.api.dto.AuthenticateUserRequestDTO;
import com.eventty.gateway.api.dto.AuthenticationDetailsResponseDTO;
import com.eventty.gateway.api.dto.GetNewTokensRequestDTO;
import com.eventty.gateway.api.dto.NewTokensResponseDTO;
import com.eventty.gateway.api.utils.MakeUrlService;
import com.eventty.gateway.global.dto.ResponseDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Collections;

@Slf4j
@Component
@AllArgsConstructor
public class ApiClient {
    private final MakeUrlService makeUrlService;
    private final RestTemplate customRestTemplate;

    public ResponseEntity<ResponseDTO<AuthenticationDetailsResponseDTO>> authenticateUser(AuthenticateUserRequestDTO authenticateUserRequestDTO) {
        HttpEntity<AuthenticateUserRequestDTO> entity = createHttpPostEntity(authenticateUserRequestDTO);
        URI uri = makeUrlService.authenticateUri();
        logApiCall("Gateway", "Auth Server", "Authenticate User");
        return executeApiCall(uri, entity);
    }

    private void logApiCall(String from, String to, String purpose) {
        log.info("API 호출 From: {} To: {} Purpose: {}", from, to, purpose);
    }

    private ResponseEntity<ResponseDTO<AuthenticationDetailsResponseDTO>> executeApiCall(URI uri, HttpEntity<AuthenticateUserRequestDTO> entity) {
        return customRestTemplate.exchange(
                uri, HttpMethod.POST, entity, new ParameterizedTypeReference<ResponseDTO<AuthenticationDetailsResponseDTO>>() {}
        );
    }

    public ResponseEntity<ResponseDTO<NewTokensResponseDTO>> getNewTokens(GetNewTokensRequestDTO getNewTokensRequestDTO) {

        HttpEntity<GetNewTokensRequestDTO> entity = createHttpPostEntity(getNewTokensRequestDTO);

        URI uri = makeUrlService.createNewTokenUri();

        // API 호출은 Loggin Level을 Info로 지정해서 로그 관리
        log.info("API 호출 From: {} To: {} Purpose: {}", "Gateway", "Auth Server", "Get New Tokens");
        return customRestTemplate.exchange(
                uri, HttpMethod.POST, entity, new ParameterizedTypeReference<ResponseDTO<NewTokensResponseDTO>>() {});
    }

    private <T> HttpEntity<T> createHttpPostEntity(T dto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        return new HttpEntity<>(dto, headers);
    }
}
