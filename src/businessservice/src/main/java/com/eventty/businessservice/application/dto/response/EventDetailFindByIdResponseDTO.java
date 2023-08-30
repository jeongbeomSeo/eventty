package com.eventty.businessservice.application.dto.response;

import com.eventty.businessservice.domain.entity.EventDetailEntity;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class EventDetailFindByIdResponseDTO {
    private Long id;
    private String content;
    private Timestamp applyStartAt;
    private Timestamp applyEndAt;
    private Long views;
    private Timestamp deleteDate;
    private Timestamp updateDate;
    private Timestamp createDate;

    public static EventDetailFindByIdResponseDTO fromEntity(EventDetailEntity eventDetailEntity) {
        return EventDetailFindByIdResponseDTO.builder()
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
