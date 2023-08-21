package com.eventty.businessservice.domains.event.application;

import com.eventty.businessservice.domains.event.domain.EventDetailEntity;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class EventDetailResponseDTO {
    private Long id;
    private String content;
    private Date applyStartAt;
    private Date applyEndAt;
    private Long views;
    private Date deleteDate;
    private Date updateDate;
    private Date createDate;

    public static EventDetailResponseDTO fromEntity(EventDetailEntity eventDetailEntity) {
        return EventDetailResponseDTO.builder()
            .id(eventDetailEntity.getId())
            .content(eventDetailEntity.getContent())
            .applyStartAt(eventDetailEntity.getApplyStartAt())
            .applyEndAt(eventDetailEntity.getApplyEndAt())
            .views(eventDetailEntity.getViews())
            .deleteDate(eventDetailEntity.getDeleteDate())
            .updateDate(eventDetailEntity.getUpdateDate())
            .createDate(eventDetailEntity.getCreateDate())
            .build();
    }

}
