package com.eventty.authservice.api.utils;

import com.eventty.authservice.api.config.UrlProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.URI;

@Service
@RequiredArgsConstructor
public class MakeUrlService {

    private final UrlProperties urlProperties;
    public URI createUserUri() {
        String CREATE_USER_API_PATH = "/api/users/me";
        return URI.create(urlProperties.getUserServer() + CREATE_USER_API_PATH);
    }
}
