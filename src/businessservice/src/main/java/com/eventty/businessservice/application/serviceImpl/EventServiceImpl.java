package com.eventty.businessservice.application.serviceImpl;

import com.eventty.businessservice.application.dto.response.EventDetailResponseDTO;
import com.eventty.businessservice.application.dto.response.EventFullResponseDTO;
import com.eventty.businessservice.application.dto.response.EventResponseDTO;
import com.eventty.businessservice.application.service.EventDetailService;
import com.eventty.businessservice.application.service.EventService;
import com.eventty.businessservice.domain.EventRepository;
import com.eventty.businessservice.domain.exception.EventNotFoundException;
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
    private final EventDetailService eventDetailService;

    @Override
    public EventFullResponseDTO findEventById(Long eventId){
        // 행사 기본 정보 (Event)
        EventResponseDTO event = Optional.ofNullable(eventRepository.selectEventById(eventId))
                .map(EventResponseDTO::fromEntity)
                .orElseThrow(()->EventNotFoundException.EXCEPTION);

        // 행사 상세 정보 (EventDetail)
        EventDetailResponseDTO eventDetail = Optional.ofNullable(eventDetailService.findEventDetailById(eventId))
                .orElseThrow(()->EventNotFoundException.EXCEPTION);

        // 행사 전체 정보 (기본 정보 + 상세 정보)
        return new EventFullResponseDTO(event, eventDetail);
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
