package com.eventty.authservice.presentation.dto;

import com.eventty.authservice.api.dto.UserCreateRequestDTO;
import com.eventty.authservice.domain.entity.AuthUserEntity;
import jakarta.validation.constraints.Email;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class FullUserCreateRequestDTO {

    @NotNull @Email
    private String email;
    @NotNull
    private String password;
    @NotNull
    private String name;
    @NotNull
    private LocalDate birth;
    @NotNull
    private String phone;
    private String address;


    // Interface PasswordEncoder로 받으면서 상황에 맞는 Encoder를 받을 수 있도록 설정
    public AuthUserEntity toAuthUserEntity(PasswordEncoder encoder) {
        return AuthUserEntity.builder()
                .email(this.email)
                .password(encoder.encode(this.password))
                .build();
    }

    // API 요청 RequestDTO 생성
    public UserCreateRequestDTO toUserCreateRequestDTO(Long authId) {
        return UserCreateRequestDTO.builder()
                .authId(authId)
                .name(this.name)
                .address(this.address)
                .birth(this.birth)
                .phone(this.phone)
                .build();
    }
}
