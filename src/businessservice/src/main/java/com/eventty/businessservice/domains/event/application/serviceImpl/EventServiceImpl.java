package com.eventty.businessservice.domains.event.application.serviceImpl;

import com.eventty.businessservice.domains.event.application.dto.EventResponseDTO;
import com.eventty.businessservice.domains.event.application.service.EventService;
import com.eventty.businessservice.domains.event.domain.EventRepository;
import com.eventty.businessservice.domains.event.domain.exception.EventNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    @Override
    public EventResponseDTO findEventById(Long id){
        return Optional.ofNullable(eventRepository.selectEventById(id))
            .map(EventResponseDTO::fromEntity)
            .orElseThrow(()->EventNotFoundException.EXCEPTION);
    }

    @Override
    public List<EventResponseDTO> findAllEvents() {
        return Optional.ofNullable(eventRepository.selectAllEvents())
            .map(events -> events.stream()
                .map(EventResponseDTO::fromEntity)
                .collect(Collectors.toList()))
            .orElseThrow(()->EventNotFoundException.EXCEPTION);
    }

}
