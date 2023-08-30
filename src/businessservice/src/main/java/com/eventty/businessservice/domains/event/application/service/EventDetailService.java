package com.eventty.businessservice.domains.event.application.service;

import com.eventty.businessservice.domains.event.application.dto.EventDetailResponseDTO;

public interface EventDetailService {
    EventDetailResponseDTO findEventDetailById(Long id);

}
