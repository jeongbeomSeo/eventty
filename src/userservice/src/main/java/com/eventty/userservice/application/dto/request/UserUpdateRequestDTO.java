package com.eventty.userservice.application.dto.request;

import lombok.*;

@Setter @Getter @Builder @ToString
@NoArgsConstructor @AllArgsConstructor
public class UserUpdateRequestDTO {
    private String name;
    private String phone;
    private String birth;
    private String address;
}
