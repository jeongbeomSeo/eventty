package com.eventty.businessservice.domains.event.application.service;

import com.eventty.businessservice.domains.event.application.dto.request.EventCreateRequestDTO;
import com.eventty.businessservice.domains.event.application.dto.request.EventUpdateRequestDTO;
import com.eventty.businessservice.domains.event.application.dto.response.EventWithTicketsFindByIdResponseDTO;
import com.eventty.businessservice.domains.event.application.dto.response.EventBasicFindAllResponseDTO;

import java.util.List;

public interface EventService {
    EventWithTicketsFindByIdResponseDTO findEventById(Long id);
    List<EventBasicFindAllResponseDTO> findAllEvents();
    Long createEvent(EventCreateRequestDTO eventCreateRequestDTO);
    Long deleteEvent(Long id);
    Long updateEvent(Long id, EventUpdateRequestDTO eventUpdateRequestDTO);
    List<EventBasicFindAllResponseDTO> findEventsByCategory(Long id);
}
