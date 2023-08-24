package com.eventty.userservice.domain;

import com.eventty.userservice.config.BooleanToYNConverter;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity @Builder @AllArgsConstructor
@Getter @NoArgsConstructor
@Table(name = "user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                    // PK값

    @NotNull
    private String name;                // 이름

    private String address;             // 주소

    private LocalDate birth;            // 생일

    @NotNull
    @Convert(converter = BooleanToYNConverter.class)
    private Boolean isHost;             // 주최 여부(주최자 일 경우 : true/참여자 일 경우 : false)

    private String image;               // 유저 사진

    private String phone;               // 유저 전화번호

    @CreationTimestamp
    private LocalDateTime createDate;   // 회원가입 일자

    @UpdateTimestamp
    private LocalDateTime updateDate;   // 회원 정보 수정 일자
}
