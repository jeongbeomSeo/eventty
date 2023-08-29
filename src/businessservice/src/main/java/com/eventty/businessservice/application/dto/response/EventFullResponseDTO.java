package com.eventty.businessservice.application.dto.response;

import lombok.*;

@Getter
@AllArgsConstructor
public class EventFullResponseDTO {
    private final EventResponseDTO eventResponseDTO;
    private final EventDetailResponseDTO eventDetailResponseDTO;
}
