package com.eventty.businessservice.application.serviceImpl;

import com.eventty.businessservice.application.dto.response.EventDetailResponseDTO;
import com.eventty.businessservice.application.dto.response.EventWithDetailDTO;
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

    // 이벤트 기본 정보와 상세 정보 모두 조회
    @Override
    public EventWithDetailDTO findEventById(Long eventId){
        return Optional.ofNullable(eventRepository.selectEventWithDetailById(eventId))
            .map(EventWithDetailDTO::fromDAO)
            .orElseThrow(()->EventNotFoundException.EXCEPTION);
    }

    // 이벤트 전체 조회
    @Override
    public List<EventResponseDTO> findAllEvents() {
        return Optional.ofNullable(eventRepository.selectAllEvents())
            .map(events -> events.stream()
                .map(EventResponseDTO::fromEntity)
                .collect(Collectors.toList()))
            .orElseThrow(()->EventNotFoundException.EXCEPTION);
    }

}
