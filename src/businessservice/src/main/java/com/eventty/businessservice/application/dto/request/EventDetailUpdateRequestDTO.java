package com.eventty.businessservice.application.dto.request;

import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
@Builder
public class EventDetailUpdateRequestDTO {
    private String content;
    private Timestamp applyStartAt;
    private Timestamp applyEndAt;
}
