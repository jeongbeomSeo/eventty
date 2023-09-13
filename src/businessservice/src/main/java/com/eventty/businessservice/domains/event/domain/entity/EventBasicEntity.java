package com.eventty.businessservice.domains.event.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class EventBasicEntity {
    private Long id;
    private Long userId; // hostId
    private String title;
    private String image;
    private LocalDateTime eventStartAt;
    private LocalDateTime eventEndAt;
    private Long participateNum;
    private String location;
    private Long category;
    private Boolean isActive;
    private Boolean isDeleted;

    public void updateTitle(String title){
        this.title = title;
    }
    public void updateImage(String image){
        this.image = image;
    }
    public void updateCategory(Long category) { this.category = category; }
}
