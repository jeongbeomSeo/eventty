package com.eventty.businessservice.event.application.dto.response;

import com.eventty.businessservice.event.domain.entity.EventBasicEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
public class EventBasicFindAllResponseDTO {
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

    public static EventBasicFindAllResponseDTO fromEntity(EventBasicEntity eventBasicEntity) {
        return EventBasicFindAllResponseDTO.builder()
            .id(eventBasicEntity.getId())
            .userId(eventBasicEntity.getUserId())
            .title(eventBasicEntity.getTitle())
            .image(eventBasicEntity.getImage())
            .eventStartAt(eventBasicEntity.getEventStartAt())
            .eventEndAt(eventBasicEntity.getEventEndAt())
            .participateNum(eventBasicEntity.getParticipateNum())
            .location(eventBasicEntity.getLocation())
            .category(eventBasicEntity.getCategory())
            .isActive(eventBasicEntity.getIsActive())
            .isDeleted(eventBasicEntity.getIsDeleted())
            .build();
    }

    // Swagger 을 위하여 기본 생성자로 기본값 설정
    public EventBasicFindAllResponseDTO() {
        this.id = 1L;
        this.userId = 1L;
        this.title = "String";
        this.image = "String";
        this.eventStartAt = LocalDateTime.now();
        this.eventEndAt = LocalDateTime.now();
        this.participateNum = 1L;
        this.location = "String";
        this.category = 1L;
        this.isActive = true;
        this.isDeleted = false;
    }
}
