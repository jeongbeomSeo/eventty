package com.eventty.authservice.presentation.dto.request;

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
