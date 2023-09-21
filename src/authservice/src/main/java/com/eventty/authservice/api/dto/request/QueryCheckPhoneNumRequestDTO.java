package com.eventty.authservice.api.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class QueryCheckPhoneNumRequestDTO {
    String phone;
}
