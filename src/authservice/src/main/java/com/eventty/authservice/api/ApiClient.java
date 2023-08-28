package com.eventty.authservice.api;

import com.eventty.authservice.api.dto.UserCreateRequestDTO;
import org.springframework.beans.factory.annotation.Value;

public class ApiClient {

    @Value("${user.server.base.url}")
    private String USER_SERVER_BASE_URL;


    public createUserApi(UserCreateRequestDTO userCreateRequestDTO) {

    }
}
