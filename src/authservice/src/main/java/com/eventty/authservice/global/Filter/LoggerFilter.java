package com.eventty.authservice.global.Filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
public class LoggerFilter extends OncePerRequestFilter {

    private final String USER_ID = "userId";
    private final String AUTHORITIES = "authorities";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // URL & Method
        log.info("This request URL is {} and method is {}\n", request.getRequestURL(), request.getMethod());
        // Authentication
        log.info("Request Header have user Id and authorities ? {} || {}",
                (request.getHeader(USER_ID) != null), (request.getHeader(AUTHORITIES) != null));
        // Client Ip Address
        log.info("Client ip address: {}", request.getRemoteAddr());

        filterChain.doFilter(request, response);
    }
}
