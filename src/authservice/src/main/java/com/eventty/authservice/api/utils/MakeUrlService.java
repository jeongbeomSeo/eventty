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

    public URI queryImgaeUri() {
        String QUERY_IMAGE_API_PATH = "/api/image";
        return URI.create(urlProperties.getUserServer() + QUERY_IMAGE_API_PATH);
    }

    public URI findUserIdUri() {
        String QUERY_CHECK_PHONE_NUM_PATH = "/api/userId";
        return URI.create(urlProperties.getUserServer() + QUERY_CHECK_PHONE_NUM_PATH);
    }
}
