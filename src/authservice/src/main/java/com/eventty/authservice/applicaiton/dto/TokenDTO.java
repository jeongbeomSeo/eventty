package com.eventty.authservice.applicaiton.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class TokenDTO {
    String accessToken;
    String refreshToken;
}
