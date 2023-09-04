package com.eventty.userservice.application.dto.response;

import com.eventty.userservice.domain.UserEntity;
import lombok.*;

import java.time.LocalDate;

@Setter @Getter @Builder @ToString
@AllArgsConstructor
public class UserFindByIdResponseDTO {
    private Long id;                    // PK값
    private Long authId;                // auth 서버의 PK 값
    private String name;                // 이름
    private String address;             // 주소
    private LocalDate birth;            // 생일
    private String image;               // 유저 사진
    private String phone;               // 유저 전화번호

    public UserFindByIdResponseDTO(UserEntity userEntity){
        this.id = userEntity.getId();
        this.authId = userEntity.getAuthId();
        this.name = userEntity.getName();
        this.address = userEntity.getAddress();
        this.birth = userEntity.getBirth();
        this.image = userEntity.getImage();
        this.phone = userEntity.getPhone();
    }
}