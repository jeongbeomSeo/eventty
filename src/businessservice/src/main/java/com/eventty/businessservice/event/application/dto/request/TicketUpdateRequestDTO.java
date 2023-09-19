package com.eventty.businessservice.event.application.dto.request;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TicketUpdateRequestDTO {
    private String name;

    @PositiveOrZero
    private Long price;
}