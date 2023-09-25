package com.eventty.authservice.presentation.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GoogleUserInfoResponseDTO {
    private String id;
    private String email;
    private Boolean verified_email;
    private String name;
    private String give_name;
    private String pricture;
    private String locale;
}