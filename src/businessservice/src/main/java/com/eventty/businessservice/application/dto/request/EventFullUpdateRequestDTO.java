package com.eventty.businessservice.application.dto.request;

import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
@Builder
public class EventFullUpdateRequestDTO {
    // Event
    private String title;
    private String image;

    // EventDetail
    private String content;

//    public EventUpdateRequestDTO toEventUpdateRequestDTO(){
//        return EventUpdateRequestDTO.builder()
//                .title(title)
//                .image(image)
//                .build();
//    }
//
//    public EventDetailUpdateRequestDTO toEventDetailUpdateRequestDTO() {
//        return EventDetailUpdateRequestDTO.builder()
//                .content(content)
//                .build();
//    }

}
