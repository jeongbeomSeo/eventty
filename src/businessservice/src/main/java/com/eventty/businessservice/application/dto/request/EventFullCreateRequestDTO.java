package com.eventty.businessservice.application.dto.request;

import com.eventty.businessservice.domain.entity.EventDetailEntity;
import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
@Builder
public class EventFullCreateRequestDTO {
    private Long id;
    private Long hostId;
    private String title;
    private String image;
    private Timestamp eventStartAt;
    private Timestamp eventEndAt;
    private Long participateNum;
    private String location;
    private String category;
    private String content;
    private Timestamp applyStartAt;
    private Timestamp applyEndAt;
    private Long views;

    public EventCreateRequestDTO toEventCreateRequestDTO(){
        return EventCreateRequestDTO.builder()
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

    public EventDetailCreateRequestDTO toEventDetailCreateRequestDTO() {
        return EventDetailCreateRequestDTO.builder()
                .id(id)
                .content(content)
                .applyStartAt(applyStartAt)
                .applyEndAt(applyEndAt)
                .build();
    }

}
