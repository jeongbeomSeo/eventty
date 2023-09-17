package com.eventty.businessservice.domains.event.application.dto.request;

import com.eventty.businessservice.domains.event.domain.entity.TicketEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TicketUpdateRequestDTO {
    private String name;
    private Long price;
    private Long quantity;
}