package com.eventty.businessservice.domains.event.domain;

import lombok.Getter;

import java.util.Date;

@Getter
public class EventEntity {
    private Long id;
    private String content;
    private Date applyStartAt;
    private Date applyEndAt;
    private Long participateNum;
    private String locate;
    private Long views;
    private Date deleteDate;
    private Date updateDate;
    private Date createDate;
}
