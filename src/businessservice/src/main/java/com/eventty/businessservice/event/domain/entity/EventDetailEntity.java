package com.eventty.businessservice.event.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class EventDetailEntity {
    private Long id;
    private String content;
    private LocalDateTime applyStartAt;
    private LocalDateTime applyEndAt;
    private Long views;
    private LocalDateTime deleteDate;
    private LocalDateTime updateDate;
    private LocalDateTime createDate;

    public void updateContent(String content){
        this.content = content;
    }
}
