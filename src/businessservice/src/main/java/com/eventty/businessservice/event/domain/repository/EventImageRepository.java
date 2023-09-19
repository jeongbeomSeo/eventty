package com.eventty.businessservice.event.domain.repository;

import com.eventty.businessservice.event.domain.entity.EventImageEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EventImageRepository {
    void insertEventImage(EventImageEntity eventImageEntity);
    //void updateEventImage(EventImageEntity eventImageEntity);
    EventImageEntity selectEventImageByEventId(Long eventId);
}
