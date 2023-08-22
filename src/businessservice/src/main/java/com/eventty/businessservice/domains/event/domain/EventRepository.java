package com.eventty.businessservice.domains.event.domain;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EventRepository {
    EventEntity selectEventById(Long id);
    List<EventEntity> selectAllEvents();
}
