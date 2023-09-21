package com.eventty.businessservice.event.application.dto.response;

import com.eventty.businessservice.event.domain.entity.TicketEntity;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
public class TicketResponseDTO {
    private Long id;
    private String name;
    private Long price;
    private Long quantity; // 티켓 수량
    private Long eventId;
    private Boolean is_deleted;

    public static TicketResponseDTO fromEntity(TicketEntity ticketEntity){
        return TicketResponseDTO.builder()
                .id(ticketEntity.getId())
                .name(ticketEntity.getName())
                .price(ticketEntity.getPrice())
                .quantity(ticketEntity.getQuantity())
                .eventId(ticketEntity.getEventId())
                .is_deleted(ticketEntity.getIs_deleted())
                .build();
    }
}
