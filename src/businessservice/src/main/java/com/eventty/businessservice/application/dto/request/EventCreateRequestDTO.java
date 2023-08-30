package com.eventty.businessservice.application.dto.request;

import com.eventty.businessservice.domain.entity.EventEntity;
import lombok.Builder;

import java.sql.Timestamp;
@Builder
public class EventCreateRequestDTO {
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

    public EventEntity toEntity() {
        return EventEntity.builder()
                .id(id)
                .hostId(hostId)
                .title(title)
                .image(image)
                .eventStartAt(eventStartAt)
                .eventEndAt(eventEndAt)
                .participateNum(participateNum)
                .location(location)
                .category(category)
                .isActive(true)
                .isDeleted(false)
                .build();
    }
}
