package com.eventty.businessservice.domains.event.application.service;

import com.eventty.businessservice.domains.event.application.dto.EventFullResponseDTO;
import com.eventty.businessservice.domains.event.application.dto.EventResponseDTO;

import java.util.List;

public interface EventService {
    EventFullResponseDTO findEventById(Long id);
    List<EventResponseDTO> findAllEvents();
}