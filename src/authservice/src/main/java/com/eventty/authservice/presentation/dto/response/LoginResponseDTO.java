package com.eventty.authservice.presentation.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class LoginResponseDTO {
    private String email;
    private List<String> authoritiesNameList;
}
