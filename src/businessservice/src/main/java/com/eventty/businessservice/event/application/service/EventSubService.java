package com.eventty.businessservice.event.application.service;

import com.eventty.businessservice.event.application.dto.request.EventCreateRequestDTO;
import com.eventty.businessservice.event.application.dto.request.EventUpdateRequestDTO;
import com.eventty.businessservice.event.application.dto.request.TicketUpdateRequestDTO;
import com.eventty.businessservice.event.application.dto.response.EventFullFindByIdResponseDTO;
import com.eventty.businessservice.event.application.dto.response.EventWithTicketsFindByIdResponseDTO;
import com.eventty.businessservice.event.domain.entity.EventBasicEntity;
import com.eventty.businessservice.event.domain.entity.EventDetailEntity;
import com.eventty.businessservice.event.domain.entity.TicketEntity;
import com.eventty.businessservice.event.domain.exception.TicketNotFoundException;
import com.eventty.businessservice.event.domain.repository.EventBasicRepository;
import com.eventty.businessservice.event.domain.exception.EventNotFoundException;
import com.eventty.businessservice.event.domain.repository.EventDetailRepository;
import com.eventty.businessservice.event.domain.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class EventSubService {

    private final EventBasicRepository eventBasicRepository;
    private final EventDetailRepository eventDetailRepository;
    private final TicketRepository ticketRepository;

    public EventWithTicketsFindByIdResponseDTO findEventById(Long eventId) {
        EventFullFindByIdResponseDTO eventWithDetail = Optional.ofNullable(eventBasicRepository.selectEventWithDetailById(eventId))
                .orElseThrow(() -> EventNotFoundException.EXCEPTION);

        List<TicketEntity> tickets = ticketRepository.selectTicketByEventId(eventId);

        // 이벤트 정보와 티켓 정보를 서비스 계층에서 통합하여 DTO 클래스에 담아 반환
        return EventWithTicketsFindByIdResponseDTO.from(eventWithDetail, tickets);
    }

    @Async("asyncExecutor")
    public void increaseView(Long eventId){

        // 요청된 ID로 객체를 찾아서 확인한 후에 업데이트를 진행
        EventFullFindByIdResponseDTO eventWithDetail = Optional.ofNullable(eventBasicRepository.selectEventWithDetailById(eventId))
                .orElseThrow(() -> EventNotFoundException.EXCEPTION);

        eventDetailRepository.updateView(eventId);
    }

    public Long updateEvent(Long id, EventUpdateRequestDTO eventUpdateRequestDTO) {
        // Event
        EventBasicEntity event = eventBasicRepository.selectEventById(id);
        event.updateTitle(eventUpdateRequestDTO.getTitle());
        event.updateImage(eventUpdateRequestDTO.getImage());
        event.updateCategory(eventUpdateRequestDTO.getCategory());

        eventBasicRepository.updateEvent(event);

        // Event Detail
        EventDetailEntity eventDetail = eventDetailRepository.selectEventDetailById(id);
        eventDetail.updateContent(eventUpdateRequestDTO.getContent());

        eventDetailRepository.updateEventDetail(eventDetail);

        return id;
    }

    public Long deleteEvent(Long id) {
        ticketRepository.deleteTicketsByEventId(id);
        eventDetailRepository.deleteEventDetail(id);
        eventBasicRepository.deleteEvent(id);
        return id;
    }

    public Long createEvent(EventCreateRequestDTO eventCreateRequestDTO) {
        // 이벤트 일반 정보 저장
        EventBasicEntity event = eventCreateRequestDTO.toEventEntity();
        eventBasicRepository.insertEvent(event);

        // 티켓 정보 저장
        eventCreateRequestDTO.getTickets().forEach(ticketCreateRequest -> {
            // 티켓 수량만큼 참여 인원 증가
            event.addParticipateNum(ticketCreateRequest.getQuantity());
            eventBasicRepository.updateEvent(event);
            // 티켓 저장
            TicketEntity ticket = ticketCreateRequest.toEntity(event.getId());
            ticketRepository.insertTicket(ticket);
        });

        // 이벤트 상세 정보 저장
        EventDetailEntity eventDetail = eventCreateRequestDTO.toEventDetailEntity(event.getId());
        return eventDetailRepository.insertEventDetail(eventDetail);
    }

    public Long updateTicket(Long ticketId, TicketUpdateRequestDTO ticketUpdateRequestDTO) {
        // 티켓 존재하는지 확인
        TicketEntity ticket = ticketRepository.selectTicketById(ticketId);
        if(ticket == null) {
            throw TicketNotFoundException.EXCEPTION;
        }

        // 티켓 가격 수정이 들어오지 않았을 경우 기존 가격 유지
        if(ticketUpdateRequestDTO.getPrice() == null){
            ticketUpdateRequestDTO.setPrice(ticket.getPrice());
        }

        // 티켓 업데이트
        ticket.updateName(ticketUpdateRequestDTO.getName());
        ticket.updatePrice(ticketUpdateRequestDTO.getPrice());
        return ticketRepository.updateTicket(ticket);
    }

    public Long deleteTicket(Long ticketId) {
        // 티켓 존재하는지 확인
        TicketEntity ticket = ticketRepository.selectTicketById(ticketId);
        if(ticket == null) {
            throw TicketNotFoundException.EXCEPTION;
        }
        // 행사 인원 수 감소
        EventBasicEntity event = eventBasicRepository.selectEventById(ticket.getEventId());
        event.subtractParticipateNum(ticket.getQuantity());
        eventBasicRepository.updateEvent(event);

        // 티켓 삭제
        return ticketRepository.deleteTicketById(ticketId);
    }

}
