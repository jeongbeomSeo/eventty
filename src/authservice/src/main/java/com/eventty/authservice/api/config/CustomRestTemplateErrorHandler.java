package com.eventty.authservice.api.config;

import com.eventty.authservice.api.exception.ApiException;
import com.eventty.authservice.common.response.ErrorResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.DefaultResponseErrorHandler;

import java.io.IOException;
import java.io.InputStream;

@Component
public class CustomRestTemplateErrorHandler extends DefaultResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        System.out.println(response.getStatusCode().is4xxClientError()
                || response.getStatusCode().is5xxServerError());
        return (
                response.getStatusCode().is4xxClientError()
                        || response.getStatusCode().is5xxServerError()
        );
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        // Read the response body as an InputStream
        InputStream responseBody = response.getBody();
        // Convert the InputStream to ErrorResponseDTO using ObjectMapper
        ErrorResponseDTO errorResponseDTO = objectMapper.readValue(responseBody, ErrorResponseDTO.class);
        throw new ApiException(errorResponseDTO, response.getStatusCode());
    }
}