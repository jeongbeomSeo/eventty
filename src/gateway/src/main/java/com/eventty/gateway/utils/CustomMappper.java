package com.eventty.gateway.utils;

import com.eventty.gateway.api.dto.GetNewTokensRequestDTO;
import org.springframework.stereotype.Component;

@Component
public class CustomMappper {
    public GetNewTokensRequestDTO createGetNewTokensRequestDTO(String userId, String refreshToken) {
        return GetNewTokensRequestDTO.builder()
                .userId(Long.parseLong(userId))
                .refreshToken(refreshToken)
                .build();
    }
}
