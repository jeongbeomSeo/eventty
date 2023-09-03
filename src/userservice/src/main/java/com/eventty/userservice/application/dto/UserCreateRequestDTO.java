package com.eventty.userservice.application.dto;

import com.eventty.userservice.domain.UserEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Setter @Getter @NoArgsConstructor
@AllArgsConstructor @Builder @ToString
public class UserCreateRequestDTO {
    private String name;
    private String address;
    private LocalDate birth;
    private String image;
    private String phone;

    public UserEntity toEntity() {
        return UserEntity
                .builder()
                .name(this.name)
                .address(this.address)
                .birth(this.birth)
                .image(this.image)
                .phone(this.phone)
                .build();
    }
}
