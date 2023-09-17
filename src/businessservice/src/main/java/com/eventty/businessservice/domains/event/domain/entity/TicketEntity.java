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

    public void updateName(String name) {
        this.name = name;
    }
    public void updatePrice(Long price) {
        this.price = price;
    }
    public void updateQuantity(Long quantity) {
        this.quantity = quantity;
    }
}
