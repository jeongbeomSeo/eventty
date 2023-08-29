package com.eventty.userservice.application.dto.response;

import com.eventty.userservice.domain.UserEntity;
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

    public UserFindByIdResponseDTO(UserEntity userEntity){
        this.id = userEntity.getId();
        this.name = userEntity.getName();
        this.address = userEntity.getAddress();
        this.birth = userEntity.getBirth();
        this.hostYn = userEntity.getIsHost();
        this.image = userEntity.getImage();
        this.phone = userEntity.getPhone();
    }
}
