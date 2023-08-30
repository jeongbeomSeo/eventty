package com.eventty.businessservice.application.dto.response;

import com.eventty.businessservice.domain.EventEntity;
import com.eventty.businessservice.domain.EventWithDetailDAO;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class EventWithDetailDTO {
    private Long id;
    private Long hostId;
    private String title;
    private String image;
    private Timestamp eventStartAt;
    private Timestamp eventEndAt;
    private Long participateNum;
    private String location;
    private String category;
    private Boolean isActive;
    private Boolean isDeleted;

    private String content;
    private Timestamp applyStartAt;
    private Timestamp applyEndAt;
    private Long views;


    public static EventWithDetailDTO fromDAO(EventWithDetailDAO event) {
        return EventWithDetailDTO.builder()
            .id(event.getId())
            .hostId(event.getHostId())
            .title(event.getTitle())
            .image(event.getImage())
            .eventStartAt(event.getEventStartAt())
            .eventEndAt(event.getEventEndAt())
            .participateNum(event.getParticipateNum())
            .location(event.getLocation())
            .category(event.getCategory())
            .isActive(event.getIsActive())
            .isDeleted(event.getIsDeleted())
            .content(event.getContent())
            .applyStartAt(event.getApplyStartAt())
            .applyEndAt(event.getApplyEndAt())
            .views(event.getViews())
            .build();
    }
}
