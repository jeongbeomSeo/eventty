package com.eventty.businessservice.domains.event.application;

import lombok.*;

import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class EventResponseDTO {
    private Long id;
    private Long eventId;
    private Long organizerId;
    private String title;
    private String image;
    private Date eventStartAt;
    private Date eventEndAt;
    private String category;
    private String isActive;
    private String isDelete;

    // fromEntity 함수 구현
}
