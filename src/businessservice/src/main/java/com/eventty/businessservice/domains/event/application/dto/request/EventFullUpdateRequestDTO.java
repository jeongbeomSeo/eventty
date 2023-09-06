package com.eventty.businessservice.domains.event.application.dto.request;

import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class EventFullUpdateRequestDTO {
    // Event
    private String title;
    private String image;
    private Long category;

    // EventDetail
    private String content;

}
