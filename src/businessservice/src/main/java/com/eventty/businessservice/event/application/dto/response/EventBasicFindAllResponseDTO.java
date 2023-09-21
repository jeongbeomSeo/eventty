package com.eventty.businessservice.event.application.dto.response;

import com.eventty.businessservice.event.domain.Enum.Category;
import com.eventty.businessservice.event.domain.entity.EventBasicEntity;
import lombok.*;

import java.time.LocalDateTime;

// 이벤트 메인페이지에서 사용할 DTO (Host 정보 미포함)
@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
public class EventBasicFindAllResponseDTO {
    private Long id;
    private Long hostId;
    private String title;
    private LocalDateTime eventStartAt;
    private LocalDateTime eventEndAt;
    private Long participateNum;
    private String location;
    private String categoryName;
    private Boolean isActive;

    public static EventBasicFindAllResponseDTO fromEntity(EventBasicEntity eventBasicEntity) {
        return EventBasicFindAllResponseDTO.builder()
                .id(eventBasicEntity.getId())
                .hostId(eventBasicEntity.getUserId())
                .title(eventBasicEntity.getTitle())
                .eventStartAt(eventBasicEntity.getEventStartAt())
                .eventEndAt(eventBasicEntity.getEventEndAt())
                .participateNum(eventBasicEntity.getParticipateNum())
                .location(eventBasicEntity.getLocation())
                .categoryName(Category.getNamefromId(eventBasicEntity.getCategory()))
                .isActive(eventBasicEntity.getIsActive())
                .build();
    }

}