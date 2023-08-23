package com.eventty.userservice.application.dto;

import com.eventty.userservice.domain.User;
import lombok.*;

import java.time.LocalDate;

@Setter
@Getter @NoArgsConstructor
@AllArgsConstructor @Builder @ToString
public class UserFindByIdResponseDTO {
    private Long id;                    // PK값
    private String name;                // 이름
    private String address;             // 주소
    private LocalDate birth;            // 생일
    private Boolean hostYn;             // 주최 여부(주최자 일 경우 : true/참여자 일 경우 : false)
    private String image;               // 유저 사진
    private String phone;               // 유저 전화번호

    public UserFindByIdResponseDTO(User user){
        this.id = user.getId();
        this.name = user.getName();
        this.address = user.getAddress();
        this.birth = user.getBirth();
        this.hostYn = user.getIsHost();
        this.image = user.getImage();
        this.phone = user.getPhone();
    }
}
