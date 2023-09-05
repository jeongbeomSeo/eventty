package com.eventty.authservice.api.dto;

import lombok.*;

import java.time.LocalDate;

/*
 User Server로 보낼 DTO
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class UserCreateRequestDTO {
    private Long userId;
    private String name;
    private String address;
    private LocalDate birth;
    private String image;
    private String phone;
}
