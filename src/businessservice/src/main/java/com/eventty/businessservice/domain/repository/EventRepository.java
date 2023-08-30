package com.eventty.businessservice.domain.repository;

import com.eventty.businessservice.application.dto.request.EventCreateRequestDTO;
import com.eventty.businessservice.domain.EventWithDetailDAO;
import com.eventty.businessservice.domain.entity.EventEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EventRepository {
    EventEntity selectEventById(Long id);
    EventWithDetailDAO selectEventWithDetailById(Long id); // JOIN된 결과를 매핑할 메서드
    List<EventEntity> selectAllEvents();
    Long insertEvent(EventEntity event);
}
