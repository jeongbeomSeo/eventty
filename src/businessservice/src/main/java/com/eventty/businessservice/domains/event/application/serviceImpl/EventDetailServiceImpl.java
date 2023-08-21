package com.eventty.businessservice.domains.event.application;

import com.eventty.businessservice.domains.event.domain.EventDetailEntity;
import com.eventty.businessservice.domains.event.domain.EventDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class EventDetailServiceImpl implements EventDetailService {

    private final EventDetailRepository eventDetailRepository;

    @Override
    public EventDetailResponseDTO findEventDetailById(Long id){
        EventDetailEntity event = eventDetailRepository.selectEventDetailById(id);
        return EventDetailResponseDTO.fromEntity(event);
    }

    /*
    @Override
    public List<EventDetailResponseDTO> findAllEvents() {
        List<EventDetailEntity> events = eventDetailRepository.selectAllEvents();
        return events.stream()
            .map(EventDetailResponseDTO::fromEntity)
            .collect(Collectors.toList());
    }

     */
}
