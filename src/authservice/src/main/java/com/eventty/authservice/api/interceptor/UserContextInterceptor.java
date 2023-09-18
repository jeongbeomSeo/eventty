package com.eventty.authservice.api.interceptor;

import com.eventty.authservice.infrastructure.Context.UserContext;
import com.eventty.authservice.infrastructure.ContextHolder.UserContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

public class UserContextInterceptor implements ClientHttpRequestInterceptor {
    private static final Logger looger = LoggerFactory.getLogger(UserContextInterceptor.class);

    //    ObjectMapper objectMapper;

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {

        HttpHeaders headers = request.getHeaders();
        headers.add(UserContext.CORRELATION_ID, UserContextHolder.getContext().getCorrelationId());

        /*
        만약, 유저 아이디가 필요한 요청과 필요하지 않은 요청을 구분해서 로직을 구성하고 싶다면,
        RestTemplate을 보낼 때 reuqest 헤더에 인증이 필요하다는 정보를 담는 것을 고려해볼만 할 것 같아요. (추측)
        이것 확인하고 싶으면 ApiClient 확인하세요

       if ("True".equals(headers.getFirst("X-Requires-Auth"))) {
             // Request Header에 상관관계 ID, User Id, User Authority담아서 보내기
            // 단, 권한은 Json으로 변환 후 보내줘야 함.
            String authoritiesJson;
            try {
                authoritiesJson = objectMapper.writeValueAsString(UserContextHolder.getContext().getAuthorities());
            } catch (JsonProcessingException e) {
                throw  new RuntimeException("Error Converting authorities to Json", e);
            }

            HttpHeaders headers = request.getHeaders();
            headers.add(UserContext.CORRELATION_ID, UserContextHolder.getContext().getCorrelationId());
            headers.add(UserContext.USER_ID, UserContextHolder.getContext().getUserId());
            headers.add(UserContext.AUTHORITIES, authoritiesJson)
       } eles {
            headers.add(UserContext.CORRELATION_ID, UserContextHolder.getContext().getCorrelationId());
       }
;*/

        return execution.execute(request, body);
    }
}
