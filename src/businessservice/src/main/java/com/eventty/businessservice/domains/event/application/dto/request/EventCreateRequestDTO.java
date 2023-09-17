package com.eventty.businessservice.domains.event.application.dto.request;

import com.eventty.businessservice.domains.event.domain.entity.EventDetailEntity;
import com.eventty.businessservice.domains.event.domain.entity.EventBasicEntity;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class EventCreateRequestDTO {
    //private Long id;
    private Long userId;
    private String title;
    private String image;
    private LocalDateTime eventStartAt;
    private LocalDateTime eventEndAt;
    private Long participateNum;
    private String location;
    private Long category;
    private String content;
    private LocalDateTime applyStartAt;
    private LocalDateTime applyEndAt;
    //private Long views;

    private List<TicketCreateRequestDTO> tickets;

    public EventBasicEntity toEventEntity(){
        return EventBasicEntity.builder()
                //.id(id)
                .userId(userId)
                .title(title)
                .image(image)
                .eventStartAt(eventStartAt)
                .eventEndAt(eventEndAt)
                .participateNum(0L)
                .location(location)
                .category(category)
                .isActive(true)
                .isDeleted(false)
                .build();
    }

    public EventDetailEntity toEventDetailEntity(Long id) {
        return EventDetailEntity.builder()
                .id(id)
                .content(content)
                .applyStartAt(applyStartAt)
                .applyEndAt(applyEndAt)
                .createDate(LocalDateTime.now())
                .updateDate(null)
                .deleteDate(null)
                .build();
    }

}
