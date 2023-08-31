package com.eventty.businessservice.application.dto.request;

import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class EventFullUpdateRequestDTO {
    // Event
    private String title;
    private String image;

    // EventDetail
    private String content;

}
