package com.eventty.authservice.presentation.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OAuthLoginRequestDTO {
    /*@NotNull
    private String accessToken;
    private String tokenType;*/
    private String code;
}
