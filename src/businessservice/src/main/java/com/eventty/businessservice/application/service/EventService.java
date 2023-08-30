package com.eventty.businessservice.application.service;

import com.eventty.businessservice.application.dto.response.EventWithDetailDTO;
import com.eventty.businessservice.application.dto.response.EventResponseDTO;

import java.util.List;

public interface EventService {
    EventWithDetailDTO findEventById(Long id);
    List<EventResponseDTO> findAllEvents();
}
