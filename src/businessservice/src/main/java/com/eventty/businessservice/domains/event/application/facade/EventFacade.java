package com.eventty.businessservice.domains.event.application.facade;

import com.eventty.businessservice.domains.event.application.dto.request.EventCreateRequestDTO;
import com.eventty.businessservice.domains.event.application.dto.request.EventUpdateRequestDTO;
import com.eventty.businessservice.domains.event.application.dto.response.EventFullFindByIdResponseDTO;
import com.eventty.businessservice.domains.event.application.dto.response.EventWithTicketsFindByIdResponseDTO;
import com.eventty.businessservice.domains.event.domain.entity.EventBasicEntity;
import com.eventty.businessservice.domains.event.domain.entity.EventDetailEntity;
import com.eventty.businessservice.domains.event.domain.entity.TicketEntity;
import com.eventty.businessservice.domains.event.domain.exception.EventNotFoundException;
import com.eventty.businessservice.domains.event.domain.repository.EventBasicRepository;
import com.eventty.businessservice.domains.event.domain.repository.EventDetailRepository;
import com.eventty.businessservice.domains.event.domain.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventFacade {

    private final EventBasicRepository eventBasicRepository;
    private final EventDetailRepository eventDetailRepository;
    private final TicketRepository ticketRepository;

    public EventWithTicketsFindByIdResponseDTO findEventById(Long eventId) {
        EventFullFindByIdResponseDTO eventWithDetail = Optional.ofNullable(eventBasicRepository.selectEventWithDetailById(eventId))
                .orElseThrow(() -> EventNotFoundException.EXCEPTION);

        List<TicketEntity> tickets = ticketRepository.selectTicketByEventId(eventId);

        // 조회수 업데이트 (비관적 락)
        increaseView(eventId);

        // 이벤트 정보와 티켓 정보를 서비스 계층에서 통합하여 DTO 클래스에 담아 반환
        return EventWithTicketsFindByIdResponseDTO.from(eventWithDetail, tickets);
    }

    public void increaseView(Long eventId){
        Long eventIdForUpdate = eventDetailRepository.selectEventForUpdate(eventId);
        if (!eventId.equals(eventIdForUpdate)) {
            throw new IllegalArgumentException("조회수 증가에 문제가 발생했습니다.");
        }
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
        ticketRepository.deleteTicket(id);
        eventDetailRepository.deleteEventDetail(id);
        eventBasicRepository.deleteEvent(id);
        return id;
    }

    public Long createEvent(EventCreateRequestDTO eventCreateRequestDTO) {
        // 이벤트 일반 정보 저장
        EventBasicEntity event = eventCreateRequestDTO.toEventEntity();
        eventBasicRepository.insertEvent(event);
        Long id = event.getId();

        // 티켓 정보 저장
        eventCreateRequestDTO.getTickets().forEach(ticketCreateRequest -> {
            TicketEntity ticket = ticketCreateRequest.toEntity(id);
            ticketRepository.insertTicket(ticket);
        });

        // 이벤트 상세 정보 저장
        EventDetailEntity eventDetail = eventCreateRequestDTO.toEventDetailEntity(id);
        return eventDetailRepository.insertEventDetail(eventDetail);
    }

}
