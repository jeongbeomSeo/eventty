package com.eventty.businessservice.event.domain.repository;

import com.eventty.businessservice.event.application.dto.response.EventFullFindByIdResponseDTO;
import com.eventty.businessservice.event.domain.entity.EventBasicEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EventBasicRepository {
    EventBasicEntity selectEventById(Long id);
    EventFullFindByIdResponseDTO selectEventWithDetailById(Long id); // JOIN된 결과를 매핑할 메서드
    List<EventBasicEntity> selectAllEvents();
    Long insertEvent(EventBasicEntity request);
    List<EventBasicEntity> selectEventsByHostId(Long hostId);
    Long updateEvent(EventBasicEntity request);
    Long deleteEvent(Long id);
    List<EventBasicEntity> selectEventsByCategory(Long categoryId);
    List<EventBasicEntity> selectEventsBySearch(String keyword);
}
