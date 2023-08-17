package com.eventty.userservice.dto;

import com.eventty.userservice.entity.User;
import lombok.*;

import java.time.LocalDate;

@Setter @Getter @NoArgsConstructor
@AllArgsConstructor @Builder @ToString
public class UserCreateRequestDTO {
    private String name;
    private String address;
    private LocalDate birth;
    private boolean isHost;
    private String image;
    private String phone;

    public User toEntity() {
        return User
                .builder()
                .name(this.name)
                .address(this.address)
                .birth(this.birth)
                .isHost(this.isHost)
                .image(this.image)
                .phone(this.phone)
                .build();
    }
}
