package com.eventty.authservice.presentation.dto.response;

import com.eventty.authservice.domain.entity.AuthorityEntity;
import lombok.*;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class LoginResponseDTO {
    private String email;
    private List<AuthorityEntity> authorityEntityList;
}
