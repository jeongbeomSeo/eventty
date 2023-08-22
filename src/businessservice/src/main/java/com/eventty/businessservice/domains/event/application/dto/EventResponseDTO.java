package com.eventty.businessservice.domains.event.application.dto;

import com.eventty.businessservice.domains.event.domain.EventEntity;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class EventResponseDTO {
    private Long id;
    private Long hostId;
    private String title;
    private String image;
    private Timestamp eventStartAt;
    private Timestamp eventEndAt;
    private Long participateNum;
    private String location;
    private String category;
    private Boolean isActive;
    private Boolean isDeleted;

    public static EventResponseDTO fromEntity(EventEntity eventEntity) {
        return EventResponseDTO.builder()
            .id(eventEntity.getId())
            .hostId(eventEntity.getHostId())
            .title(eventEntity.getTitle())
            .image(eventEntity.getImage())
            .eventStartAt(eventEntity.getEventStartAt())
            .eventEndAt(eventEntity.getEventEndAt())
            .participateNum(eventEntity.getParticipateNum())
            .location(eventEntity.getLocation())
            .category(eventEntity.getCategory())
            .isActive(eventEntity.getIsActive())
            .isDeleted(eventEntity.getIsDeleted())
            .build();
    }
}
