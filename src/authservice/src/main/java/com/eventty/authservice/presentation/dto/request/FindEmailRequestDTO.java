package com.eventty.authservice.presentation.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FindEmailRequestDTO {
    String phone;
}
