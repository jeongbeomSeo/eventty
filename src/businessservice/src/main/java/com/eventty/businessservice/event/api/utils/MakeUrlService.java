package com.eventty.businessservice.event.api.utils;

import com.eventty.businessservice.event.api.config.UrlProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.URI;

@Service
@RequiredArgsConstructor
public class MakeUrlService {

    private final UrlProperties urlProperties;
    public URI queryHostInfo(Long hostId) {
        String QUERY_USER_INFO_API_PATH = "/users/me?host=" + hostId;
        return URI.create(urlProperties.getUserServer() + QUERY_USER_INFO_API_PATH);
        /*
            String QUERY_USER_INFO_API_PATH = "/users/me?host=%d";
            return URI.create(String.format(urlProperties.getUserServer() + QUERY_USER_INFO_API_PATH, hostId));
         */
    }

    public URI queryTicketCount() {
        String QUERY_TICKET_COUNT_API_PATH = "/api/applies/count";
        return URI.create(urlProperties.getApplyServer() + QUERY_TICKET_COUNT_API_PATH);
    }
}
