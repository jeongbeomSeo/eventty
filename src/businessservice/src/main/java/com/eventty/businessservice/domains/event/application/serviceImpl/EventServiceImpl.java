package com.eventty.businessservice.domains.event.application;

import com.eventty.businessservice.domains.event.domain.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;


    @Override
    EventResponseDTO findEventById(Long id){

    }
}
