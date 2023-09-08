package com.eventty.businessservice.domains.event.application.dto.response;

import com.eventty.businessservice.domains.event.domain.entity.TicketEntity;
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
    private String image;
    private LocalDateTime eventStartAt;
    private LocalDateTime eventEndAt;
    private Long participateNum;
    private String location;
    //private Long category;
    private String categoryName;
    private Boolean isActive;
    private Boolean isDeleted;
    private String content;
    private LocalDateTime applyStartAt;
    private LocalDateTime applyEndAt;
    private Long views;

    private List<TicketEntity> tickets;

    public static EventWithTicketsFindByIdResponseDTO from(EventFullFindByIdResponseDTO event, List<TicketEntity> tickets) {
        return EventWithTicketsFindByIdResponseDTO.builder()
            .id(event.getId())
            .userId(event.getUserId())
            .title(event.getTitle())
            .image(event.getImage())
            .eventStartAt(event.getEventStartAt())
            .eventEndAt(event.getEventEndAt())
            .participateNum(event.getParticipateNum())
            .location(event.getLocation())
            .categoryName(event.getCategoryName())
            .isActive(event.getIsActive())
            .isDeleted(event.getIsDeleted())
            .content(event.getContent())
            .applyStartAt(event.getApplyStartAt())
            .applyEndAt(event.getApplyEndAt())
            .views(event.getViews())
            .tickets(tickets)
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
        this.categoryName = "String";
        this.isActive = true;
        this.isDeleted = false;
        this.content = "String";
        this.applyStartAt = LocalDateTime.now();
        this.applyEndAt = LocalDateTime.now();
        this.views = 1L;
        this.tickets = new ArrayList<>();
    }
}
