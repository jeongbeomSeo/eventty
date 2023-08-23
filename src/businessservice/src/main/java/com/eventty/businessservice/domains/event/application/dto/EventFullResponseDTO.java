package com.eventty.businessservice.domains.event.application.dto;

import lombok.*;

@Getter
@AllArgsConstructor
public class EventFullResponseDTO {
    private final EventResponseDTO eventResponseDTO;
    private final EventDetailResponseDTO eventDetailResponseDTO;
}
