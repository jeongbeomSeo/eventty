package com.eventty.businessservice.domains.event.domain.repository;

import com.eventty.businessservice.domains.event.domain.entity.EventBasicEntity;
import com.eventty.businessservice.domains.event.application.dto.response.EventFullFindByIdResponseDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EventBasicRepository {
    EventBasicEntity selectEventById(Long id);
    EventFullFindByIdResponseDTO selectEventWithDetailById(Long id); // JOIN된 결과를 매핑할 메서드
    List<EventBasicEntity> selectAllEvents();
    Long insertEvent(EventBasicEntity request);
    Long updateEvent(EventBasicEntity request);
    Long deleteEvent(Long id);
    List<EventBasicEntity> selectEventsByCategory(Long categoryId);
}
