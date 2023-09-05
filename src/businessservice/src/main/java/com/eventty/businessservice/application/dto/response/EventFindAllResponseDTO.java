package com.eventty.businessservice.application.dto.response;

import com.eventty.businessservice.domain.entity.EventEntity;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
public class EventFindAllResponseDTO {
    private Long id;
    private Long hostId;
    private String title;
    private String image;
    private Timestamp eventStartAt;
    private Timestamp eventEndAt;
    private Long participateNum;
    private String location;
    private Long category;
    private Boolean isActive;
    private Boolean isDeleted;

    public static EventFindAllResponseDTO fromEntity(EventEntity eventEntity) {
        return EventFindAllResponseDTO.builder()
            .id(eventEntity.getId())
            .hostId(eventEntity.getHostId())
            .title(eventEntity.getTitle())
            .image(eventEntity.getImage())
            .eventStartAt(eventEntity.getEventStartAt())
            .eventEndAt(eventEntity.getEventEndAt())
            .participateNum(eventEntity.getParticipateNum())
            .location(eventEntity.getLocation())
            .category(eventEntity.getCategory())
            .isActive(eventEntity.getIsActive())
            .isDeleted(eventEntity.getIsDeleted())
            .build();
    }

    // Swagger 을 위하여 기본 생성자로 기본값 설정
    public EventFindAllResponseDTO() {
        this.id = 1L;
        this.hostId = 1L;
        this.title = "String";
        this.image = "String";
        this.eventStartAt = Timestamp.valueOf(LocalDateTime.now());
        this.eventEndAt = Timestamp.valueOf(LocalDateTime.now());
        this.participateNum = 1L;
        this.location = "String";
        this.category = 1L;
        this.isActive = true;
        this.isDeleted = false;
    }
}
