package com.eventty.userservice.application.dto.request;

import com.eventty.userservice.domain.UserEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Setter @Getter @NoArgsConstructor
@AllArgsConstructor @Builder @ToString
public class UserCreateRequestDTO {
    @NotBlank(message = "a null value or '' value or ' ' value.")
    private String name;
    private String address;
    private LocalDate birth;
    @NotNull(message = "a null value.")
    private Boolean isHost;
    private String image;
    private String phone;

    public UserEntity toEntity() {
        return UserEntity
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
