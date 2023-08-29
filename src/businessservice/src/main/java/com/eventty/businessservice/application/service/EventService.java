package com.eventty.businessservice.application.service;

import com.eventty.businessservice.application.dto.response.EventFullResponseDTO;
import com.eventty.businessservice.application.dto.response.EventResponseDTO;

import java.util.List;

public interface EventService {
    EventFullResponseDTO findEventById(Long id);
    List<EventResponseDTO> findAllEvents();
}
