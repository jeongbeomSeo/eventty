package com.eventty.authservice.presentation.dto;

import jakarta.validation.constraints.Email;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Builder
public class IsUserDuplicateRequestDTO {
    @Email
    String email;
}
