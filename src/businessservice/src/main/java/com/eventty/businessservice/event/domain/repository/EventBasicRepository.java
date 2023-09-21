package com.eventty.businessservice.event.domain.repository;

import com.eventty.businessservice.event.domain.entity.EventBasicEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EventBasicRepository {
    EventBasicEntity selectEventById(Long id);
    List<EventBasicEntity> selectEventsByIdList(List<Long> idList);
    List<EventBasicEntity> selectAllEvents();
    Long insertEvent(EventBasicEntity request);
    List<EventBasicEntity> selectEventsByHostId(Long hostId);
    Long updateEvent(EventBasicEntity request);
    Long deleteEvent(Long id);
    List<EventBasicEntity> selectEventsByCategory(Long categoryId);
    List<EventBasicEntity> selectEventsBySearch(String keyword);
}
