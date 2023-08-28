package com.eventty.authservice.applicaiton.dto;

import jakarta.validation.constraints.Email;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Builder
public class IsUserDuplicateDTO {
    @Email
    String email;
}
