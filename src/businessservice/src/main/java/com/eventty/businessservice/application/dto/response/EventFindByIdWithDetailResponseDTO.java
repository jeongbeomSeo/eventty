package com.eventty.businessservice.application.dto.response;

import com.eventty.businessservice.domain.entity.TicketEntity;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
public class EventFindByIdWithDetailResponseDTO {
    private Long id;
    private Long hostId;
    private String title;
    private String image;
    private Timestamp eventStartAt;
    private Timestamp eventEndAt;
    private Long participateNum;
    private String location;
    //private Long category;
    private String categoryName;
    private Boolean isActive;
    private Boolean isDeleted;
    private String content;
    private Timestamp applyStartAt;
    private Timestamp applyEndAt;
    private Long views;

    private List<TicketEntity> tickets;

    public static EventFindByIdWithDetailResponseDTO from(EventWithDetailResponseDTO event, List<TicketEntity> tickets) {
        return EventFindByIdWithDetailResponseDTO.builder()
            .id(event.getId())
            .hostId(event.getHostId())
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
    public EventFindByIdWithDetailResponseDTO() {
        this.id = 1L;
        this.hostId = 1L;
        this.title = "String";
        this.image = "String";
        this.eventStartAt = Timestamp.valueOf(LocalDateTime.now());
        this.eventEndAt = Timestamp.valueOf(LocalDateTime.now());
        this.participateNum = 1L;
        this.location = "String";
        this.categoryName = "String";
        this.isActive = true;
        this.isDeleted = false;
        this.content = "String";
        this.applyStartAt = Timestamp.valueOf(LocalDateTime.now());
        this.applyEndAt = Timestamp.valueOf(LocalDateTime.now());
        this.views = 1L;
        this.tickets = new ArrayList<>();
    }
}
