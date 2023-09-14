package com.eventty.gateway.service;

import com.eventty.gateway.dto.TokenDetails;
import org.springframework.http.HttpCookie;
import org.springframework.util.MultiValueMap;

public interface TokenAuthenticationService {
    TokenDetails getTokenDetails(MultiValueMap<String, HttpCookie> cookies);
}
