package com.eventty.userservice.application.dto.response;

import com.eventty.userservice.domain.UserEntity;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserCreateAndUpdateResponseDTO {
    @NotNull
    private Long id;

    public UserCreateAndUpdateResponseDTO(UserEntity userEntity){
        this.id = userEntity.getId();
    }
}
