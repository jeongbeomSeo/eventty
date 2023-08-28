package com.eventty.authservice.domain.entity;

import com.eventty.authservice.infrastructure.BooleanToYNConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "auth_user")
public class AuthUserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;                    // PK값

    @Column(nullable = false, unique = true)
    private String email;               // Email

    private String password;            // 암호화된 Password

    @Column(nullable = false)
    @Convert(converter = BooleanToYNConverter.class)
    private Boolean isHost;             // 주최 여부(주최자 일 경우 : true/참여자 일 경우 : false)

}
