package com.eventty.businessservice.event.api.utils;

import com.eventty.businessservice.event.api.config.UrlProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.URI;

@Service
@RequiredArgsConstructor
public class MakeUrlService {

    private final UrlProperties urlProperties;
    public URI queryUserInfo() {
        String QUERY_USER_INFO_API_PATH = "/users/me";
        return URI.create(urlProperties.getUserServer() + QUERY_USER_INFO_API_PATH);
    }

    public URI queryTicketCount() {
        String QUERY_TICKET_COUNT_API_PATH = "/api/applies/count";
        return URI.create(urlProperties.getApplyServer() + QUERY_TICKET_COUNT_API_PATH);
    }
}
