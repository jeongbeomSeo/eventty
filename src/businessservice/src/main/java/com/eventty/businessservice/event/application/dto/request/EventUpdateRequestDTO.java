package com.eventty.businessservice.event.application.dto.request;

import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;


@Getter
@Builder
public class EventUpdateRequestDTO {
    // Event
    private String title;

    private String category;

    private Boolean isActive;

    // EventDetail
    private String content;

    // image
    private MultipartFile image;

}
