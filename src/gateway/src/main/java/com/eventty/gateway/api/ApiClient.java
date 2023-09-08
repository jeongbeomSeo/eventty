package com.eventty.gateway.api;

import com.eventty.gateway.api.dto.GetNewTokensRequestDTO;
import com.eventty.gateway.api.dto.NewTokensResponseDTO;
import com.eventty.gateway.api.utils.MakeUrlService;
import com.eventty.gateway.presentation.dto.ResponseDTO;
import com.eventty.gateway.presentation.dto.SuccessResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Collections;

@Component
@AllArgsConstructor
public class ApiClient {

    private final MakeUrlService makeUrlService;
    private final RestTemplate customRestTemplate;

    public ResponseEntity<ResponseDTO<NewTokensResponseDTO>> getNewTokens(GetNewTokensRequestDTO getNewTokensRequestDTO) {

        HttpEntity<GetNewTokensRequestDTO> entity = createHttpPostEntity(getNewTokensRequestDTO);

        URI uri = makeUrlService.getNewTokenUri();

        return customRestTemplate.exchange(uri, HttpMethod.POST, entity, new ParameterizedTypeReference<ResponseDTO<NewTokensResponseDTO>>() {});
    }

    private <T> HttpEntity<T> createHttpPostEntity(T dto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        return new HttpEntity<>(dto, headers);
    }
}
