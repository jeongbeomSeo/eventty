package com.eventty.businessservice.domain;

import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface EventDetailRepository {
    EventDetailEntity selectEventDetailById(Long id);
}
