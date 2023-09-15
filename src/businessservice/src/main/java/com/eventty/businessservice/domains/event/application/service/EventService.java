package com.eventty.businessservice.domains.event.application.service;

import com.eventty.businessservice.domains.event.application.dto.request.EventCreateRequestDTO;
import com.eventty.businessservice.domains.event.application.dto.request.EventUpdateRequestDTO;
import com.eventty.businessservice.domains.event.application.dto.response.EventBasicFindAllResponseDTO;
import com.eventty.businessservice.domains.event.application.dto.response.EventWithTicketsFindByIdResponseDTO;
import com.eventty.businessservice.domains.event.domain.exception.CategoryNotFoundException;
import com.eventty.businessservice.domains.event.domain.exception.EventNotFoundException;
import com.eventty.businessservice.domains.event.domain.repository.EventBasicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventBasicRepository eventBasicRepository;
    private final EventSubService eventSubService; // 복잡한 로직의 경우 하위 서비스 호출 (Facade 패턴)

    /**
     * 이벤트 상세 조회 (이벤트에 대한 모든 정보 + 티켓 정보)
     */
    public EventWithTicketsFindByIdResponseDTO findEventById(Long eventId){
        // 조회수 증가 (비동기로 처리)
        eventSubService.increaseView(eventId);
        return eventSubService.findEventById(eventId);
    }

    /**
     * 이벤트 전체 조회
     */
    @Transactional(readOnly = true)
    public List<EventBasicFindAllResponseDTO> findAllEvents() {
        return Optional.ofNullable(eventBasicRepository.selectAllEvents())
                .map(events -> events.stream()
                .map(EventBasicFindAllResponseDTO::fromEntity)
                .collect(Collectors.toList()))
                .orElseThrow(()->EventNotFoundException.EXCEPTION);
    }

    /**
     * 이벤트 생성
     */
    public Long createEvent(EventCreateRequestDTO eventCreateRequestDTO){
        return eventSubService.createEvent(eventCreateRequestDTO);
    }

    /**
     * 이벤트 수정
     */
    public Long updateEvent(Long id, EventUpdateRequestDTO eventUpdateRequestDTO){
        return eventSubService.updateEvent(id, eventUpdateRequestDTO);
    }

    /**
     * 이벤트 삭제
     */
    public Long deleteEvent(Long id){
        return eventSubService.deleteEvent(id);
    }

    /**
     * 이벤트 카테고리별 조회
     */
    @Transactional(readOnly = true)
    public List<EventBasicFindAllResponseDTO> findEventsByCategory(Long categoryId){

        if (categoryId < 1 || categoryId > 10) {
            throw CategoryNotFoundException.EXCEPTION;
        }

        return Optional.ofNullable(eventBasicRepository.selectEventsByCategory(categoryId))
                .filter(events -> !events.isEmpty())
                .orElseThrow(() -> EventNotFoundException.EXCEPTION)
                .stream()
                .map(EventBasicFindAllResponseDTO::fromEntity)
                .collect(Collectors.toList());

    }

    /**
     * 행사 검색
     */
    @Transactional(readOnly = true)
    public List<EventBasicFindAllResponseDTO> findEventsBySearch(String keyword){
        return Optional.ofNullable(eventBasicRepository.selectEventsBySearch(keyword))
                .filter(events -> !events.isEmpty())
                .orElseThrow(() -> EventNotFoundException.EXCEPTION)
                .stream()
                .map(EventBasicFindAllResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

}
