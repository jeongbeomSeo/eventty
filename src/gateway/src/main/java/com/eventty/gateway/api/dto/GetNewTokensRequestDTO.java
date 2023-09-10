package com.eventty.gateway.api.dto;


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
