package com.eventty.applyservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class ApplyEntity {
    private Long id;
    private Long userId;
    private Long eventId;
    private Long ticketId;
    private LocalDateTime applyDate;
    private LocalDateTime deleteDate;
}
