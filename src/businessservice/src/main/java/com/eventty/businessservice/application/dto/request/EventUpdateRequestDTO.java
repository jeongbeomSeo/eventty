package com.eventty.businessservice.application.dto.request;

import com.eventty.businessservice.domain.entity.EventEntity;
import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
@Builder
public class EventUpdateRequestDTO {
    private String title;
    private String image;
    private Timestamp eventStartAt;
    private Timestamp eventEndAt;
    private Long participateNum;
    private String location;
    private Long category;

    public EventEntity toEntity(Long id) {
        return EventEntity.builder()
                .id(id)
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
