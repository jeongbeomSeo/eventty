package com.eventty.userservice.application.dto.request;

import lombok.*;

import java.time.LocalDate;

@Setter @Getter @Builder @ToString
@NoArgsConstructor @AllArgsConstructor
public class UserUpdateRequestDTO {
    private String name;
    private String phone;
    private LocalDate birth;
    private String address;
    private String image;
}
