package com.eventty.authservice.api.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class QueryCheckPhoneNumResponseDTO {
    Long userId;
}
