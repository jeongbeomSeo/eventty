package com.eventty.businessservice.event.application.dto.request;

import com.eventty.businessservice.event.domain.entity.TicketEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TicketCreateRequestDTO {
    private String name;
    private Long price;
    private Long quantity;

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