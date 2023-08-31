package com.eventty.businessservice.application.dto.request;

import java.sql.Timestamp;

public class EventFullUpdateRequestDTO {
    // Event
    private String title;
    private String image;
    private Timestamp eventStartAt;
    private Timestamp eventEndAt;
    private Long participateNum;
    private String location;
    private String category;

    // EventDetail
    private String content;
    private Timestamp applyStartAt;
    private Timestamp applyEndAt;

    public EventUpdateRequestDTO toEventUpdateRequestDTO(){
        return EventUpdateRequestDTO.builder()
                .title(title)
                .image(image)
                .eventStartAt(eventStartAt)
                .eventEndAt(eventEndAt)
                .participateNum(participateNum)
                .location(location)
                .category(category)
                .build();
    }

    public EventDetailUpdateRequestDTO toEventDetailUpdateRequestDTO() {
        return EventDetailUpdateRequestDTO.builder()
                .content(content)
                .applyStartAt(applyStartAt)
                .applyEndAt(applyEndAt)
                .build();
    }

}
