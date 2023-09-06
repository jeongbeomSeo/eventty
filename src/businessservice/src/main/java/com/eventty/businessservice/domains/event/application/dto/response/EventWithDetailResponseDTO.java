package com.eventty.businessservice.domains.event.application.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Builder
public class EventWithDetailResponseDTO {
    private Long id;
    private Long userId;
    private String title;
    private String image;
    private LocalDateTime eventStartAt;
    private LocalDateTime eventEndAt;
    private Long participateNum;
    private String location;
    private Long category;
    private Boolean isActive;
    private Boolean isDeleted;
    private String content;
    private LocalDateTime applyStartAt;
    private LocalDateTime applyEndAt;
    private Long views;
    private LocalDateTime delete_date;
    private LocalDateTime update_date;
    private LocalDateTime create_date;
    private String categoryName;
}
