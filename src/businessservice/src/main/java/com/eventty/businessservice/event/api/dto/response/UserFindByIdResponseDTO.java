package com.eventty.businessservice.event.api.dto.response;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
public class UserFindByIdResponseDTO {
    private Long userId;
    private String name;                // 이름
    private String address;             // 주소
    private LocalDate birth;            // 생일
    private String phone;               // 유저 전화번호
    private Long imageId;
    private String image;
    private String originFileName;
}
