package com.eventty.userservice.infrastructure;

import com.eventty.userservice.domain.exception.PermissionDeniedException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

    private final String HEADER_USER_ID = "X-User-Id";
    private final String HEADER_AUTHORITIES = "X-User-Authorities";
    private final String USER_ID = "userId";
    private final String AUTHORITIES = "authorities";

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 헤더에서 user Id 와 권한 정보 추출
        String userId = request.getHeader(HEADER_USER_ID);
        String authorities = request.getHeader(HEADER_AUTHORITIES);

        // Authentication가 필요한 데이터가 담겨오지 않았다면 Token이 필요하지 않은 요청이라고 판단하고 넘기기
        if (userId == null || userId.isEmpty()) return true;

        // 임시로 HttpServletRequest의 속성에 저장
        request.setAttribute(USER_ID, userId);
        request.setAttribute(AUTHORITIES, authorities);

        // SecurityContextHolder Setting
        setSecurityContextHolder(userId, authorities);

        return true;
    }

    /**
     * SecurityContextHolder 세팅
     * 
     * @param userId
     * @param authJSONString
     */
    private void setSecurityContextHolder(String userId, String authJSONString){
        Authentication authentication = new UsernamePasswordAuthenticationToken(userId, "", generateGrantesAuthories(jsonParse(authJSONString)));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        log.info("Successfully saved {}'s authentication in the security context holder.", authentication.getPrincipal());
    }

    /**
     * json형태의 String을 List<Map> 으로 변환
     *
     * @param authJSONString
     * @return List<Map<String, String>>
     */
    private List<Map<String, String>> jsonParse(String authJSONString){
        try{
            return objectMapper.readValue(authJSONString, List.class);
        }catch (Exception e){
            log.error("Error converting JSON to authorities");
            throw new PermissionDeniedException();
        }
    }

    /**
     * json에서 parsing한 값에서 authority만 가져와서 List로 반환
     * 
     * @param authorities
     * @return List<GrantedAuthority>
     */
    private List<GrantedAuthority> generateGrantesAuthories(List<Map<String, String>> authorities){
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        for(Map<String, String> auth : authorities){
            String role = auth.get("authority");
            if(role != null && !"".equals(role)){
                grantedAuthorities.add(new SimpleGrantedAuthority(role));
            }
        }

        return grantedAuthorities;
    }
}
