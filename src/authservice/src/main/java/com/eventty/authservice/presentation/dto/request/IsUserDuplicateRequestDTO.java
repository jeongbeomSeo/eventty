package com.eventty.authservice.presentation.dto.request;

import jakarta.validation.constraints.Email;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class IsUserDuplicateRequestDTO {
    @Email
    String email;
}
