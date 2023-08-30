package com.eventty.businessservice.application.service;

import com.eventty.businessservice.application.dto.response.EventDetailFindByIdResponseDTO;
import com.eventty.businessservice.domain.entity.EventDetailEntity;

public interface EventDetailService {
    EventDetailFindByIdResponseDTO findEventDetailById(Long id);
    void createEventDetail(EventDetailEntity eventDetail);
}
