package com.eventty.businessservice.event.domain.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TicketEntity {
    private Long id;
    private String name;
    private Long price;
    private Long quantity; // 티켓 수량
    private Long eventId;
    private Boolean is_deleted;

    public void updateName(String name) {
        this.name = name;
    }
    public void updatePrice(Long price) {
        this.price = price;
    }
}
