package com.eventty.businessservice.domains.event.application.Facade;

import com.eventty.businessservice.domains.event.application.dto.request.EventCreateRequestDTO;
import com.eventty.businessservice.domains.event.application.dto.request.EventUpdateRequestDTO;
import com.eventty.businessservice.domains.event.application.dto.response.EventBasicFindAllResponseDTO;
import com.eventty.businessservice.domains.event.application.dto.response.EventWithTicketsFindByIdResponseDTO;

import java.util.List;

public interface EventService {

    public EventWithTicketsFindByIdResponseDTO findEventById(Long eventId);

    public List<EventBasicFindAllResponseDTO> findAllEvents();

    public Long createEvent(EventCreateRequestDTO eventCreateRequestDTO);

    public Long updateEvent(Long id, EventUpdateRequestDTO eventUpdateRequestDTO);

    public Long deleteEvent(Long id);

    public List<EventBasicFindAllResponseDTO> findEventsByCategory(Long categoryId);

}
