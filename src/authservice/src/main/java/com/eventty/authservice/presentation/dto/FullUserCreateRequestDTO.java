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
    private String password;

    @NotNull
    private String nickname;

    @NotNull
    private String name;
    private String address;
    private LocalDate birth;
    @NotNull
    private Boolean isHost;
    private String image;
    private String phone;

    // Interface PasswordEncoder로 받으면서 상황에 맞는 Encoder를 받을 수 있도록 설정
    public AuthUserEntity toAuthUserEntity(PasswordEncoder encoder) {
        return AuthUserEntity.builder()
                .email(this.email)
                .password(encoder.encode(this.password))
                .isHost(this.isHost)
                .build();
    }

    // API 요청 RequestDTO 생성
    public UserCreateRequestDTO toUserCreateRequestDTO() {
        return UserCreateRequestDTO.builder()
                .name(this.name)
                .address(this.address)
                .birth(this.birth)
                .image(this.image)
                .phone(this.phone)
                .build();
    }
}
