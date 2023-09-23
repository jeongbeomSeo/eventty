package com.eventty.businessservice.event.application.dto.request;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TicketUpdateRequestDTO {
    private String name;

    @PositiveOrZero
    private Long price;
}