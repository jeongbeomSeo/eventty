package com.eventty.businessservice.domains.event.domain;

import com.eventty.businessservice.domains.event.domain.EventEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EventRepository {
    EventEntity selectEventById(Long id);

    List<EventEntity> selectAllEvents();

    //void insertEvent(EventEntity event);

    //void updateEvent(EventEntity event);

    //void deleteEvent(Long id);
}
