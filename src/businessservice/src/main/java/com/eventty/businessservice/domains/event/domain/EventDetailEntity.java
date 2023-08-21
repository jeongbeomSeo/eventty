package com.eventty.businessservice.domains.event.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;
import java.util.Date;

@Getter
@AllArgsConstructor
@Builder
public class EventDetailEntity {
    private Long id;
    private String content;
    private Timestamp applyStartAt;
    private Timestamp applyEndAt;
    private Long views;
    private Timestamp deleteDate;
    private Timestamp updateDate;
    private Timestamp createDate;
}
