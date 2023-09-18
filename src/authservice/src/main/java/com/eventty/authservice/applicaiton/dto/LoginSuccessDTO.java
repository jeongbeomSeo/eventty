package com.eventty.authservice.applicaiton.dto;

import com.eventty.authservice.presentation.dto.response.LoginResponseDTO;
import lombok.*;

public record LoginSuccessDTO (
        TokensDTO tokensDTO,
        LoginResponseDTO loginResponseDTO,
        String csrfToken
){
}
