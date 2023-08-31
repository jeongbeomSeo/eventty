package com.eventty.businessservice.domain.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TicketEntity {
    private Long id;
    private String name;
    private double price;
    private int quantity;
    private Long eventId;
    private Boolean is_deleted;
}
