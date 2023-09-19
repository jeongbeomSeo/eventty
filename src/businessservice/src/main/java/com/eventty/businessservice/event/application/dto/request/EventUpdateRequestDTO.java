package com.eventty.businessservice.event.application.dto.request;

import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;


@Getter
@Builder
public class EventUpdateRequestDTO {
    // Event
    private String title;
    private Long imageId;
    private MultipartFile image;

    @Min(1)
    private Long category;

    // EventDetail
    private String content;

}
