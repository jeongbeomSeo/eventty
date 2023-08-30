package com.eventty.businessservice.domain;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EventRepository {
    EventEntity selectEventById(Long id);
    EventWithDetailDAO selectEventWithDetailById(Long id); // JOIN된 결과를 매핑할 메서드
    List<EventEntity> selectAllEvents();
}
