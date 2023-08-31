package com.eventty.businessservice.application.dto.request;

import com.eventty.businessservice.domain.entity.TicketEntity;
import lombok.Getter;

@Getter
public class TicketCreateRequestDTO {
    private String name;
    private double price;
    private int quantity;

    public TicketEntity toEntity(Long eventId){
        return TicketEntity.builder()
                .eventId(eventId)
                .name(name)
                .price(price)
                .quantity(quantity)
                .is_deleted(false)
                .build();
    }
}