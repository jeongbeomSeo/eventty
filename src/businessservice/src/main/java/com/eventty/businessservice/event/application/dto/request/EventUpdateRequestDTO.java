package com.eventty.businessservice.event.application.dto.request;

import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class EventUpdateRequestDTO {
    // Event
    private String title;
    private String image;

    @Min(1)
    private Long category;

    // EventDetail
    private String content;

}
