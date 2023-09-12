package com.eventty.businessservice.domains.event.domain.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TicketEntity {
    private Long id;
    private String name;
    private Long price;
    private Long quantity;
    private Long eventId;
    private Boolean is_deleted;
}
