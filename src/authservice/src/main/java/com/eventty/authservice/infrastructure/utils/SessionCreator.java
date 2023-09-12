package com.eventty.authservice.infrastructure.utils;

import com.eventty.authservice.applicaiton.dto.LoginSuccessDTO;
import com.eventty.authservice.domain.entity.AuthorityEntity;
import com.eventty.authservice.presentation.dto.response.LoginResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public class SessionCreator {

    public static void createSession(HttpServletRequest request, LoginResponseDTO loginResponseDTO) {
        HttpSession session = request.getSession();
        session.setAttribute("email", loginResponseDTO.getEmail());
        session.setAttribute("authorities", convertAuthorities(
                loginResponseDTO.getAuthorityEntityList()));

        session.setMaxInactiveInterval(2 * 60 * 60);  // 2시간
    }

    private static List<String> convertAuthorities(List<AuthorityEntity> list) {
        return list.stream().map(AuthorityEntity::getName).toList();
    }
}
