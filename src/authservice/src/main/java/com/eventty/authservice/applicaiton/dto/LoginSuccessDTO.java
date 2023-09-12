package com.eventty.authservice.applicaiton.dto;

import com.eventty.authservice.presentation.dto.response.LoginResponseDTO;
import lombok.*;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class LoginSuccessDTO {
    TokensDTO tokensDTO;
    LoginResponseDTO loginResponseDTO;
}
