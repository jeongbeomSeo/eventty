package com.eventty.authservice.presentation.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class UserLoginRequestDTO {

    @NotNull @Email
    String email;

    @NotNull
    String password;

}
