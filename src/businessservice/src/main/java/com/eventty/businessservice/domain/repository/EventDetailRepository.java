package com.eventty.businessservice.domain.repository;

import com.eventty.businessservice.application.dto.request.EventDetailCreateRequestDTO;
import com.eventty.businessservice.application.dto.request.EventDetailUpdateRequestDTO;
import com.eventty.businessservice.domain.entity.EventDetailEntity;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface EventDetailRepository {
    EventDetailEntity selectEventDetailById(Long id);
    Long insertEventDetail(EventDetailCreateRequestDTO request);
    Long updateEventDetail(EventDetailEntity eventDetail);
    Long deleteEventDetail(Long id);
}
