package com.eventty.businessservice.event.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class
EventBasicEntity {
    private Long id;
    private Long hostId;
    private String title;
    private LocalDateTime eventStartAt;
    private LocalDateTime eventEndAt;
    private Long participateNum;
    private String location;
    private Long category;
    private Boolean isActive;
    private Boolean isDeleted;

    public void subtractParticipateNum(Long participateNumPerTicket){
        this.participateNum -= participateNumPerTicket;
    }
    public void updateTitle(String title){
        this.title = title;
    }
    public void updateCategory(Long category) { this.category = category; }
    public void updateIsActive(Boolean isActive) { this.isActive = isActive; }
}
