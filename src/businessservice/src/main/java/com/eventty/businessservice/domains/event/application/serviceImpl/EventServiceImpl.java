package com.eventty.businessservice.domains.event.application.serviceImpl;

import com.eventty.businessservice.domains.event.application.dto.EventResponseDTO;
import com.eventty.businessservice.domains.event.application.service.EventService;
import com.eventty.businessservice.domains.event.domain.EventEntity;
import com.eventty.businessservice.domains.event.domain.EventRepository;
import com.eventty.businessservice.domains.event.exception.EventNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    @Override
    public EventResponseDTO findEventById(Long id){
        EventEntity event = eventRepository.selectEventById(id);
        if(event == null){
            throw EventNotFoundException.EXCEPTION;
        }
        return EventResponseDTO.fromEntity(event);
    }

    @Override
    public List<EventResponseDTO> findAllEvents() {
        List<EventEntity> events = eventRepository.selectAllEvents();
        return events.stream()
            .map(EventResponseDTO::fromEntity)
            .collect(Collectors.toList());
    }

}
