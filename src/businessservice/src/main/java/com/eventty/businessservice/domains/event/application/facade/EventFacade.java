package com.eventty.businessservice.domains.event.application.facade;

import com.eventty.businessservice.domains.event.application.dto.request.EventCreateRequestDTO;
import com.eventty.businessservice.domains.event.domain.entity.EventBasicEntity;
import com.eventty.businessservice.domains.event.domain.entity.EventDetailEntity;
import com.eventty.businessservice.domains.event.domain.entity.TicketEntity;
import com.eventty.businessservice.domains.event.domain.repository.EventBasicRepository;
import com.eventty.businessservice.domains.event.domain.repository.EventDetailRepository;
import com.eventty.businessservice.domains.event.domain.repository.TicketRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@AllArgsConstructor
public class EventFacade {

    private EventBasicRepository eventBasicRepository;
    private EventDetailRepository eventDetailRepository;
    private TicketRepository ticketRepository;

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
