package com.eventty.businessservice.event.application.dto.response;

import com.eventty.businessservice.event.domain.entity.EventBasicEntity;
import com.eventty.businessservice.event.domain.entity.EventDetailEntity;
import com.eventty.businessservice.event.domain.entity.TicketEntity;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
public class EventWithTicketsFindByIdResponseDTO {
    private Long id;
    private Long userId;
    private String title;
    private Long imageId; // 이미지 ID
    private LocalDateTime eventStartAt;
    private LocalDateTime eventEndAt;
    private Long participateNum;
    private String location;
    private Long category;
    //private String categoryName;
    private Boolean isActive;
    private Boolean isDeleted;
    private String content;
    private LocalDateTime applyStartAt;
    private LocalDateTime applyEndAt;
    private Long views;

    private List<TicketEntity> tickets;

    private String image; // 이미지 파일
    private String originFileName; // 원본 파일명

    public static EventWithTicketsFindByIdResponseDTO from(
            EventBasicEntity eventBasic,
            EventDetailEntity eventDetail,
            List<TicketEntity> tickets,
            String base64EncodedFile,
            String originFileName) {
        return EventWithTicketsFindByIdResponseDTO.builder()
            .id(eventBasic.getId())
            .userId(eventBasic.getUserId())
            .title(eventBasic.getTitle())
            .imageId(eventBasic.getImageId())
            .eventStartAt(eventBasic.getEventStartAt())
            .eventEndAt(eventBasic.getEventEndAt())
            .participateNum(eventBasic.getParticipateNum())
            .location(eventBasic.getLocation())
            .category(eventBasic.getCategory())
            .isActive(eventBasic.getIsActive())
            .isDeleted(eventBasic.getIsDeleted())
            .content(eventDetail.getContent())
            .applyStartAt(eventDetail.getApplyStartAt())
            .applyEndAt(eventDetail.getApplyEndAt())
            .views(eventDetail.getViews())
            .tickets(tickets)
            .image(base64EncodedFile)
            .originFileName(originFileName)
            .build();
    }


    // Swagger 을 위하여 기본 생성자로 기본값 설정
    public EventWithTicketsFindByIdResponseDTO() {
        this.id = 1L;
        this.userId = 1L;
        this.title = "String";
        this.image = "String";
        this.eventStartAt = LocalDateTime.now();
        this.eventEndAt = LocalDateTime.now();
        this.participateNum = 1L;
        this.location = "String";
        this.category = 2L;
        this.isActive = true;
        this.isDeleted = false;
        this.content = "String";
        this.applyStartAt = LocalDateTime.now();
        this.applyEndAt = LocalDateTime.now();
        this.views = 1L;
        this.tickets = new ArrayList<>();
    }
}
