package com.eventty.authservice.domain.entity;

import lombok.*;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;
import java.util.List;

@Getter
// Setter의 경우 TestCode때문에 넣었어요
@Setter
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

    @Column(nullable = false)
    private String password;            // 암호화된 Password

    @ColumnDefault("false")
    @Column(name = "is_delete", nullable = false)
    private boolean isDelete;

    private LocalDateTime deleteDate;

    @OneToMany(mappedBy = "authUserEntity", fetch = FetchType.EAGER)
    private List<AuthorityEntity> Authorities;
}
