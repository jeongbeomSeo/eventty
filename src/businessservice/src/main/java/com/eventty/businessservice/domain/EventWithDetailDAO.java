package com.eventty.businessservice.domain;

import lombok.Getter;

import java.sql.Timestamp;

@Getter
public class EventWithDetailDAO {
    private Long id;
    private Long hostId;
    private String title;
    private String image;
    private Timestamp eventStartAt;
    private Timestamp eventEndAt;
    private Long participateNum;
    private String location;
    private String category;
    private Boolean isActive;
    private Boolean isDeleted;

    private String content;
    private Timestamp applyStartAt;
    private Timestamp applyEndAt;
    private Long views;
}
