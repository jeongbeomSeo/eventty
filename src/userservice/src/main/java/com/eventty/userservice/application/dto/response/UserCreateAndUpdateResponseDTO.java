package com.eventty.userservice.application.dto.response;

import com.eventty.userservice.domain.UserEntity;
import lombok.*;

@Setter @Getter @Builder @ToString
@AllArgsConstructor
public class UserCreateAndUpdateResponseDTO {

    private Long id;

    public UserCreateAndUpdateResponseDTO(UserEntity userEntity){
        this.id = userEntity.getId();
    }
}
