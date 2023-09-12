package com.eventty.businessservice.domains.event.domain.repository;

import com.eventty.businessservice.domains.event.domain.entity.EventDetailEntity;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface EventDetailRepository {
    EventDetailEntity selectEventDetailById(Long id);
    Long insertEventDetail(EventDetailEntity request);
    Long selectEventForUpdate(Long id);
    Long updateEventDetail(EventDetailEntity request);
    Long updateView(Long id);
    Long deleteEventDetail(Long id);
}
