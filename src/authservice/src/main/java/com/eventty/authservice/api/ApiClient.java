package com.eventty.authservice.api;

import com.eventty.authservice.api.dto.request.QueryCheckPhoneNumRequestDTO;
import com.eventty.authservice.api.dto.request.UserCreateRequestDTO;
import com.eventty.authservice.api.dto.response.QueryCheckPhoneNumResponseDTO;
import com.eventty.authservice.api.dto.response.QueryImageResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import org.springframework.http.*;

import java.net.URI;
import java.util.Collections;

import lombok.AllArgsConstructor;

import com.eventty.authservice.api.utils.MakeUrlService;
import com.eventty.authservice.global.response.ResponseDTO;

@Slf4j
@Component
@AllArgsConstructor
public class ApiClient {

    // @Bean에 이름을 지정하지 않아서 생성자 이름을 따라감
    private final MakeUrlService makeUrlService;

    private final RestTemplate basicRestTemplate;

    private final RestTemplate customRestTemplate;

    public ResponseEntity<ResponseDTO<Void>> createUserApi(UserCreateRequestDTO userCreateRequestDTO) {

        HttpEntity<UserCreateRequestDTO> entity = createHttpEntity(userCreateRequestDTO);

        URI uri = makeUrlService.createUserUri();

        // API 호출은 Loggin Level을 Info로 지정해서 로그 관리
        logApiCall("Auth Server", "User server", "Create User");
        return customRestTemplate.exchange(
                uri, HttpMethod.POST, entity, new ParameterizedTypeReference<ResponseDTO<Void>>() {}
        );
    }

    public ResponseEntity<ResponseDTO<QueryImageResponseDTO>> queryImageApi() {

        HttpEntity<Void> entity = createAuthenticateHttpEntity(null);

        URI uri = makeUrlService.queryImgaeUri();

        logApiCall("Auth Server", "User Server", "Query Image");
        return customRestTemplate.exchange(
                uri, HttpMethod.GET, entity, new ParameterizedTypeReference<ResponseDTO<QueryImageResponseDTO>>() {}
        );
    }

    public ResponseEntity<ResponseDTO<QueryCheckPhoneNumResponseDTO>> queryPhoneNumberApi(QueryCheckPhoneNumRequestDTO queryCheckPhoneNumRequestDTO) {

        HttpEntity<QueryCheckPhoneNumRequestDTO> entity = createHttpEntity(queryCheckPhoneNumRequestDTO);

        URI uri = makeUrlService.queryCheckPhoneNumUri();

        logApiCall("Auth Server", "User Server", "Query Phone Number");
        return customRestTemplate.exchange(
                uri, HttpMethod.POST, entity, new ParameterizedTypeReference<ResponseDTO<QueryCheckPhoneNumResponseDTO>>() {}
        );
    }

    private void logApiCall(String from, String to, String purpose) {
        log.info("API 호출 From: {} To: {} Purpose: {}", from, to, purpose);
    }

    private <T> HttpEntity<T> createHttpEntity(T dto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        return new HttpEntity<>(dto, headers);
    }

    private <T> HttpEntity<T> createAuthenticateHttpEntity(T dto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("X-Requires-Auth", "True");

        return new HttpEntity<>(dto, headers);
    }
}