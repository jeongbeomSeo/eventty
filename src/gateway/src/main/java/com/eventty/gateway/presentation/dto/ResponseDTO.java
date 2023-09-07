package com.eventty.gateway.presentation.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@ToString
public class ResponseDTO {

    private final boolean isSuccess = false;
    private ErrorResponseDTO errorResponseDTO;

    private ResponseDTO(ErrorResponseDTO errorResponseDTO) {
        this.errorResponseDTO = errorResponseDTO;
    }

    public static ResponseDTO of (ErrorResponseDTO errorResponseDTO) {
        return new ResponseDTO(errorResponseDTO);
    }

}
