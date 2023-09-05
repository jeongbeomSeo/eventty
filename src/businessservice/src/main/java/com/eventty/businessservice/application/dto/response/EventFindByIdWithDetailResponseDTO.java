package com.eventty.businessservice.application.dto.response;

import com.eventty.businessservice.domain.entity.TicketEntity;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
}
