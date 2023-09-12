package com.eventty.businessservice.domains.event.application.serviceImpl;

import com.eventty.businessservice.domains.event.application.dto.request.EventCreateRequestDTO;
import com.eventty.businessservice.domains.event.application.dto.request.EventUpdateRequestDTO;
import com.eventty.businessservice.domains.event.application.dto.response.EventWithTicketsFindByIdResponseDTO;
import com.eventty.businessservice.domains.event.application.dto.response.EventBasicFindAllResponseDTO;
import com.eventty.businessservice.domains.event.application.facade.EventFacade;
import com.eventty.businessservice.domains.event.application.service.EventService;
import com.eventty.businessservice.domains.event.domain.exception.CategoryNotFoundException;
import com.eventty.businessservice.domains.event.domain.repository.EventBasicRepository;
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

    private final EventBasicRepository eventBasicRepository;
    private final EventFacade eventFacade; // 여러 Repository 의 메소드를 사용하는 로직의 경우 Facade 패턴 적용

    /**
     * 이벤트 상세 조회 (이벤트에 대한 모든 정보 + 티켓 정보)
     */
    @Override
    public EventWithTicketsFindByIdResponseDTO findEventById(Long eventId){
        // 조회수 증가 (비동기로 처리)
        eventFacade.increaseView(eventId);
        return eventFacade.findEventById(eventId);
    }

    /**
     * 이벤트 전체 조회
     */
    @Override
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
    @Override
    public Long createEvent(EventCreateRequestDTO eventCreateRequestDTO){
        return eventFacade.createEvent(eventCreateRequestDTO);
    }

    /**
     * 이벤트 수정
     */
    @Override
    public Long updateEvent(Long id, EventUpdateRequestDTO eventUpdateRequestDTO){
        return eventFacade.updateEvent(id, eventUpdateRequestDTO);
    }

    /**
     * 이벤트 삭제
     */
    @Override
    public Long deleteEvent(Long id){
        return eventFacade.deleteEvent(id);
    }

    /**
     * 이벤트 카테고리별 조회
     */
    @Override
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

}
