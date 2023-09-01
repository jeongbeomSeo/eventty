package com.eventty.businessservice.domain.repository;

import com.eventty.businessservice.domain.EventWithDetailDTO;
import com.eventty.businessservice.domain.entity.EventEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EventRepository {
    EventEntity selectEventById(Long id);
    EventWithDetailDTO selectEventWithDetailById(Long id); // JOIN된 결과를 매핑할 메서드
    List<EventEntity> selectAllEvents();
    Long insertEvent(EventEntity request);
    Long updateEvent(EventEntity request);
    Long deleteEvent(Long id);
    List<EventEntity> selectEventsByCategory(Long categoryId);
}
