package com.eventty.businessservice.application.service;

import com.eventty.businessservice.application.dto.response.EventDetailResponseDTO;

public interface EventDetailService {
    EventDetailResponseDTO findEventDetailById(Long id);

}
