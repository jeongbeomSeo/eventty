package com.eventty.businessservice.domains.event.application.serviceImpl;

import com.eventty.businessservice.domains.event.application.dto.request.EventCreateRequestDTO;
import com.eventty.businessservice.domains.event.application.dto.request.EventUpdateRequestDTO;
import com.eventty.businessservice.domains.event.application.dto.response.EventWithTicketsFindByIdResponseDTO;
import com.eventty.businessservice.domains.event.application.dto.response.EventBasicFindAllResponseDTO;
import com.eventty.businessservice.domains.event.application.facade.EventFacade;
import com.eventty.businessservice.domains.event.application.service.EventService;
import com.eventty.businessservice.domains.event.application.dto.response.EventFullFindByIdResponseDTO;
import com.eventty.businessservice.domains.event.domain.entity.EventDetailEntity;
import com.eventty.businessservice.domains.event.domain.entity.EventBasicEntity;
import com.eventty.businessservice.domains.event.domain.entity.TicketEntity;
import com.eventty.businessservice.domains.event.domain.exception.CategoryNotFoundException;
import com.eventty.businessservice.domains.event.domain.repository.EventDetailRepository;
import com.eventty.businessservice.domains.event.domain.repository.EventBasicRepository;
import com.eventty.businessservice.domains.event.domain.exception.EventNotFoundException;
import com.eventty.businessservice.domains.event.domain.repository.TicketRepository;
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

    private final EventFacade eventFacade;

    /**
     * 이벤트 상세 조회 (이벤트에 대한 모든 정보 + 티켓 정보)
     */
    @Override
    public EventWithTicketsFindByIdResponseDTO findEventById(Long eventId){
        return eventFacade.findEventById(eventId);
    }

    /**
     * 이벤트 전체 조회
     */
    @Override
    public List<EventBasicFindAllResponseDTO> findAllEvents() {
        return eventFacade.findAllEvents();
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
    public List<EventBasicFindAllResponseDTO> findEventsByCategory(Long categoryId){
        return eventFacade.findEventsByCategory(categoryId);
    }

}
