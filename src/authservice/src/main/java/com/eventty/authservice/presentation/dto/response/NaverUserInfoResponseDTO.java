package com.eventty.authservice.presentation.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class NaverUserInfoResponseDTO {
    String id;
    String name;
    String email;
    String birthday;
    String brithyear;
    String profileImage;
    String mobile;
}
