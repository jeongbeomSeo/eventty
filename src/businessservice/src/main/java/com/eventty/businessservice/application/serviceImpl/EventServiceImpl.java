package com.eventty.businessservice.application.serviceImpl;

import com.eventty.businessservice.application.dto.request.*;
import com.eventty.businessservice.application.dto.response.EventFindByIdWithDetailResponseDTO;
import com.eventty.businessservice.application.dto.response.EventFindAllResponseDTO;
import com.eventty.businessservice.application.service.EventService;
import com.eventty.businessservice.application.dto.response.EventWithDetailResponseDTO;
import com.eventty.businessservice.domain.entity.EventDetailEntity;
import com.eventty.businessservice.domain.entity.EventEntity;
import com.eventty.businessservice.domain.entity.TicketEntity;
import com.eventty.businessservice.domain.exception.CategoryNotFoundException;
import com.eventty.businessservice.domain.repository.EventDetailRepository;
import com.eventty.businessservice.domain.repository.EventRepository;
import com.eventty.businessservice.domain.exception.EventNotFoundException;
import com.eventty.businessservice.domain.repository.TicketRepository;
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
    private final TicketRepository ticketRepository;

    // 이벤트 기본 정보와 상세 정보 모두 조회
    @Override
    public EventFindByIdWithDetailResponseDTO findEventById(Long eventId){

        // 이벤트 정보와 티켓 정보를 서비스 계층에서 통합하여 DTO 클래스에 담아 반환 예정
        EventWithDetailResponseDTO eventWithDetail = eventRepository.selectEventWithDetailById(eventId);
        List<TicketEntity> tickets = ticketRepository.selectTicketByEventId(eventId);

        // 조회수 업데이트 쿼리 execute
        eventDetailRepository.updateView(eventId);

        // 티켓이 없을 경우 "티켓이 모두 매진되었습니다."

        if(eventWithDetail == null){
            throw EventNotFoundException.EXCEPTION;
        }

        return EventFindByIdWithDetailResponseDTO.from(eventWithDetail, tickets);
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
    public Long createEvent(EventFullCreateRequestDTO eventFullCreateRequestDTO){

        // 이벤트 일반 정보 저장
        EventEntity event = eventFullCreateRequestDTO.toEventEntity();
        eventRepository.insertEvent(event);
        Long id = event.getId();

        // 티켓 정보 저장
        eventFullCreateRequestDTO.getTickets().forEach(ticketCreateRequest -> {
            TicketEntity ticket = ticketCreateRequest.toEntity(id);
            ticketRepository.insertTicket(ticket);
        });

        // 이벤트 상세 정보 저장
        EventDetailEntity eventDetail = eventFullCreateRequestDTO.toEventDetailEntity(id);
        return eventDetailRepository.insertEventDetail(eventDetail);
    }

    // 이벤트 수정
    @Override
    public Long updateEvent(Long id, EventFullUpdateRequestDTO eventFullUpdateRequestDTO){

        // Event
        EventEntity event = eventRepository.selectEventById(id);
        event.updateTitle(eventFullUpdateRequestDTO.getTitle());
        event.updateImage(eventFullUpdateRequestDTO.getImage());
        event.updateCategory(eventFullUpdateRequestDTO.getCategory());

        eventRepository.updateEvent(event);

        // Event Detail
        EventDetailEntity eventDetail = eventDetailRepository.selectEventDetailById(id);
        eventDetail.updateContent(eventFullUpdateRequestDTO.getContent());

        eventDetailRepository.updateEventDetail(eventDetail);

        return id;
    }

    // 이벤트 삭제
    @Override
    public Long deleteEvent(Long id){
        ticketRepository.deleteTicket(id);
        eventDetailRepository.deleteEventDetail(id);
        eventRepository.deleteEvent(id);
        return id;
    }

    // 이벤트 카테고리별 조회
    @Override
    public List<EventFindAllResponseDTO> findEventsByCategory(Long categoryId){

        if (categoryId < 1 || categoryId > 10) {
            throw CategoryNotFoundException.EXCEPTION;
        }

        return Optional.ofNullable(eventRepository.selectEventsByCategory(categoryId))
                .filter(events -> !events.isEmpty())
                .orElseThrow(() -> EventNotFoundException.EXCEPTION)
                .stream()
                .map(EventFindAllResponseDTO::fromEntity)
                .collect(Collectors.toList());

    }

}
