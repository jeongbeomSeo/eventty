package com.eventty.userservice.application.dto.request;

import com.eventty.userservice.domain.UserEntity;
import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserUpdateRequestDTO {
    private Long id;
    private String name;
    private String phone;
    private LocalDate birth;
    private String address;
    private String image;
}
