package com.eventty.authservice.api.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;

@Service
public class MakeUrlService {

    @Value("${user.server.base.url}")
    private String USER_SERVER_BASE_URL;
    public URI createUserUri() {
        return URI.create(USER_SERVER_BASE_URL + "/api/users/me");
    }
}
