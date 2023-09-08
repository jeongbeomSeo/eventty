package com.eventty.authservice.presentation.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class GetNewTokensRequestDTO {
    private Long userId;
    private String refreshToken;
}
