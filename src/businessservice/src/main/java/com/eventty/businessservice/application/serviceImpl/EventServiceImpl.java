package com.eventty.businessservice.application.serviceImpl;

import com.eventty.businessservice.application.dto.request.EventCreateRequestDTO;
import com.eventty.businessservice.application.dto.request.EventDetailCreateRequestDTO;
import com.eventty.businessservice.application.dto.request.EventFullCreateRequestDTO;
import com.eventty.businessservice.application.dto.response.EventFindByIdWithDetailDTO;
import com.eventty.businessservice.application.dto.response.EventFindAllResponseDTO;
import com.eventty.businessservice.application.service.EventService;
import com.eventty.businessservice.domain.repository.EventDetailRepository;
import com.eventty.businessservice.domain.repository.EventRepository;
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
    private final EventDetailRepository eventDetailRepository;

    // 이벤트 기본 정보와 상세 정보 모두 조회
    @Override
    public EventFindByIdWithDetailDTO findEventById(Long eventId){
        return Optional.ofNullable(eventRepository.selectEventWithDetailById(eventId))
            .map(EventFindByIdWithDetailDTO::fromDAO)
            .orElseThrow(()->EventNotFoundException.EXCEPTION);
    }

    // 이벤트 전체 조회
    @Override
    public List<EventFindAllResponseDTO> findAllEvents() {
        return Optional.ofNullable(eventRepository.selectAllEvents())
            .map(events -> events.stream()
                .map(EventFindAllResponseDTO::fromEntity)
                .collect(Collectors.toList()))
            .orElseThrow(()->EventNotFoundException.EXCEPTION);
    }

    // 이벤트 생성
    @Override
    public void createEvent(EventFullCreateRequestDTO eventFullCreateRequestDTO){

        EventCreateRequestDTO eventCreateRequestDTO = eventFullCreateRequestDTO.toEventCreateRequestDTO();
        EventDetailCreateRequestDTO eventDetailCreateRequestDTO = eventFullCreateRequestDTO.toEventDetailCreateRequestDTO();

        eventRepository.insertEvent(eventCreateRequestDTO.toEntity());
        eventDetailRepository.insertEventDetail(eventDetailCreateRequestDTO.toEntity());

    }

    // 이벤트 삭제
    @Override
    public Long deleteEvent(Long id){
        Long deletedEventId = null;
        // 두 개의 메서드 모두 성공적으로 삭제되어 같은 ID 반환하고 있는지 확인 후 이벤트 ID를 반환
        if (eventDetailRepository.deleteEventDetail(id) == eventRepository.deleteEvent(id)) {
            deletedEventId = id;
        }
        return deletedEventId;
    }
}
