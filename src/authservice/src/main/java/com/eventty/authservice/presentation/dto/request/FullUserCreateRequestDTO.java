package com.eventty.authservice.presentation.dto.request;

import jakarta.validation.constraints.Email;

import jakarta.validation.constraints.NotNull;
import lombok.*;


import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class FullUserCreateRequestDTO {

    @NotNull @Email
    private String email;
    @NotNull
    private String password;
    private String name;
    @NotNull
    private LocalDate birth;
    @NotNull
    private String phone;
    private String address;
}
