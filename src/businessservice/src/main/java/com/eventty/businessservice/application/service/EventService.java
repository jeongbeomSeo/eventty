package com.eventty.businessservice.application.service;

import com.eventty.businessservice.application.dto.request.EventFullCreateRequestDTO;
import com.eventty.businessservice.application.dto.request.EventFullUpdateRequestDTO;
import com.eventty.businessservice.application.dto.response.EventFindByIdWithDetailResponseDTO;
import com.eventty.businessservice.application.dto.response.EventFindAllResponseDTO;

import java.util.List;

public interface EventService {
    EventFindByIdWithDetailResponseDTO findEventById(Long id);
    List<EventFindAllResponseDTO> findAllEvents();
    Long createEvent(EventFullCreateRequestDTO eventFullCreateRequestDTO);
    Long deleteEvent(Long id);
    Long updateEvent(Long id, EventFullUpdateRequestDTO eventFullUpdateRequestDTO);
    List<EventFindAllResponseDTO> findEventsByCategory(Long id);
}
