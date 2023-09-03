package com.eventty.authservice.api;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import org.springframework.http.*;

import com.eventty.authservice.api.dto.UserCreateRequestDTO;
import com.eventty.authservice.api.utils.MakeUrlService;
import com.eventty.authservice.common.response.ResponseDTO;

import java.net.URI;
import java.util.Collections;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ApiClient {

    private final MakeUrlService makeUrlService;

    // @Bean에 이름을 지정하지 않아서 생성자 이름을 따라감
    private final RestTemplate basicRestTemplate;

    private final RestTemplate customRestTemplate;

    public ResponseEntity<ResponseDTO> createUserApi(UserCreateRequestDTO userCreateRequestDTO) {

        HttpEntity<UserCreateRequestDTO> entity = createHttpPostEntity(userCreateRequestDTO);

        URI uri = makeUrlService.createUserUri();

        return customRestTemplate.exchange(uri, HttpMethod.POST, entity, ResponseDTO.class);
    }
    private <T> HttpEntity<T> createHttpPostEntity(T dto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        //headers.add("X-CSRF-TOKEN", "value");

        return new HttpEntity<>(dto, headers);
    }
}