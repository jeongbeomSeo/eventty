package com.eventty.businessservice.event.application.dto.response;

import com.eventty.businessservice.event.domain.Enum.Category;
import com.eventty.businessservice.event.domain.entity.EventBasicEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
public class EventBasicResponseDTO {
    private Long id;
    private Long userId;
    private String title;
    private LocalDateTime eventStartAt;
    private LocalDateTime eventEndAt;
    private Long participateNum;
    private String location;
    private String categoryName;
    private Boolean isActive;
    private Boolean isDeleted;

    public static EventBasicResponseDTO fromEntity(EventBasicEntity eventBasicEntity) {
        return EventBasicResponseDTO.builder()
            .id(eventBasicEntity.getId())
            .userId(eventBasicEntity.getUserId())
            .title(eventBasicEntity.getTitle())
            .eventStartAt(eventBasicEntity.getEventStartAt())
            .eventEndAt(eventBasicEntity.getEventEndAt())
            .participateNum(eventBasicEntity.getParticipateNum())
            .location(eventBasicEntity.getLocation())
            .categoryName(Category.getNamefromId(eventBasicEntity.getCategory()))
            .isActive(eventBasicEntity.getIsActive())
            .isDeleted(eventBasicEntity.getIsDeleted())
            .build();
    }

    // Swagger 을 위하여 기본 생성자로 기본값 설정
    public EventBasicResponseDTO() {
        this.id = 1L;
        this.userId = 1L;
        this.title = "String";
        this.eventStartAt = LocalDateTime.now();
        this.eventEndAt = LocalDateTime.now();
        this.participateNum = 1L;
        this.location = "String";
        this.isActive = true;
        this.isDeleted = false;
    }
}