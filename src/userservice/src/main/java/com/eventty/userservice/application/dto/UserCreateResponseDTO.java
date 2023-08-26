package com.eventty.userservice.application.dto;

import com.eventty.userservice.domain.UserEntity;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserCreateResponseDTO {
    @NotNull
    private Long id;

    public UserCreateResponseDTO(UserEntity userEntity){
        this.id = userEntity.getId();
    }
}
