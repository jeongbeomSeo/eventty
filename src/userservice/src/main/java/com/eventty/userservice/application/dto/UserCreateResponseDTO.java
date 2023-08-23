package com.eventty.userservice.application.dto;

import com.eventty.userservice.domain.User;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserCreateResponseDTO {
    private Long id;

    public UserCreateResponseDTO(User user){
        this.id = user.getId();
    }
}
