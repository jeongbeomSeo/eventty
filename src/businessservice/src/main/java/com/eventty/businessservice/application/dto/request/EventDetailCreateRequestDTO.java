package com.eventty.businessservice.application.dto.request;

import com.eventty.businessservice.domain.entity.EventDetailEntity;
import lombok.Builder;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Builder
public class EventDetailCreateRequestDTO {
    private Long id;
    private String content;
    private Timestamp applyStartAt;
    private Timestamp applyEndAt;
 //   private Long views;
//    private Timestamp deleteDate;
//    private Timestamp updateDate;
//    private Timestamp createDate;

    public EventDetailEntity toEntity() {
        return EventDetailEntity.builder()
            .id(id)
            .content(content)
            .applyStartAt(applyStartAt)
            .applyEndAt(applyEndAt)
            .deleteDate(null)
            .updateDate(null)
            .createDate(Timestamp.valueOf(LocalDateTime.now()))
            .build();
    }
}
