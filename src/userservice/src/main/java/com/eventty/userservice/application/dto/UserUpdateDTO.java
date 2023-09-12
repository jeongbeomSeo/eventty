package com.eventty.userservice.application.dto;

import com.eventty.userservice.application.dto.request.UserUpdateRequestDTO;
import com.eventty.userservice.domain.UserEntity;
import lombok.*;

import java.time.LocalDate;
import java.util.StringTokenizer;

@Setter @Getter @Builder @ToString
@AllArgsConstructor
public class UserUpdateDTO {

    private Long userId;
    private String name;
    private String phone;
    private LocalDate birth;
    private String address;
    private String image;

    public UserUpdateDTO(UserEntity user){
        this.userId = user.getUserId();
        this.name = user.getName();
        this.phone = user.getPhone();
        this.birth = user.getBirth();
        this.address = user.getAddress();
        this.image = user.getImage();
    }

    public UserEntity toEntity(UserUpdateRequestDTO dto){
        return UserEntity
                .builder()
                .userId(this.userId)
                .name(dto.getName() != null ? dto.getName() : this.name)
                .phone(dto.getPhone() != null ? dto.getPhone() : this.phone)
                .birth(dto.getBirth() != null ? StringToLocalDate(dto.getBirth()) : this.birth)
                .address(dto.getAddress() != null ? dto.getAddress() : this.address)
                .image(dto.getImage() != null ? dto.getImage() : this.image)
                .build();
    }

    private LocalDate StringToLocalDate(String birth){
        if("".equals(birth)){
            return null;
        }

        StringTokenizer st = new StringTokenizer(birth,"-");
        return LocalDate.of(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
    }
}