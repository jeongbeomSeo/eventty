package com.eventty.userservice.application.dto.request;

import com.eventty.userservice.domain.UserEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Setter @Getter @Builder @ToString
public class UserCreateRequestDTO {
    @NotNull(message = "a null value")
    private Long authId;
    @NotBlank(message = "a null value or '' value or ' ' value")
    private String name;
    private String address;
    private LocalDate birth;
    private String image;
    private String phone;

    public UserEntity toEntity() {
        return UserEntity
                .builder()
                .authId(this.authId)
                .name(this.name)
                .address(this.address)
                .birth(this.birth)
                .image(this.image)
                .phone(this.phone)
                .build();
    }
}
