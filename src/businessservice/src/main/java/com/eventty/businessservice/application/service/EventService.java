package com.eventty.businessservice.application.service;

import com.eventty.businessservice.application.dto.request.EventCreateRequestDTO;
import com.eventty.businessservice.application.dto.request.EventFullCreateRequestDTO;
import com.eventty.businessservice.application.dto.response.EventFindByIdWithDetailDTO;
import com.eventty.businessservice.application.dto.response.EventFindAllResponseDTO;

import java.util.List;

public interface EventService {
    EventFindByIdWithDetailDTO findEventById(Long id);
    List<EventFindAllResponseDTO> findAllEvents();
    void createEvent(EventFullCreateRequestDTO eventFullCreateRequestDTO);
    Long deleteEvent(Long id);
}
