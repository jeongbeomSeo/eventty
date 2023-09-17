package com.eventty.businessservice.domains.event.application.service;

import com.eventty.businessservice.domains.event.application.dto.request.TicketUpdateRequestDTO;
import com.eventty.businessservice.domains.event.domain.Enum.Category;
import com.eventty.businessservice.domains.event.application.dto.request.EventCreateRequestDTO;
import com.eventty.businessservice.domains.event.application.dto.request.EventUpdateRequestDTO;
import com.eventty.businessservice.domains.event.application.dto.response.EventBasicFindAllResponseDTO;
import com.eventty.businessservice.domains.event.application.dto.response.EventWithTicketsFindByIdResponseDTO;
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

    public EventWithTicketsFindByIdResponseDTO findEventById(Long eventId){
        // 조회수 증가 (비동기로 처리)
        eventSubService.increaseView(eventId);
        return eventSubService.findEventById(eventId);
    }

    @Transactional(readOnly = true)
    public List<EventBasicFindAllResponseDTO> findAllEvents() {
        return Optional.ofNullable(eventBasicRepository.selectAllEvents())
                .map(events -> events.stream()
                .map(EventBasicFindAllResponseDTO::fromEntity)
                .collect(Collectors.toList()))
                .orElseThrow(()->EventNotFoundException.EXCEPTION);
    }

    public Long createEvent(EventCreateRequestDTO eventCreateRequestDTO){
        return eventSubService.createEvent(eventCreateRequestDTO);
    }

    public Long updateTicket(Long ticketId, TicketUpdateRequestDTO ticketUpdateRequestDTO){
        return eventSubService.updateTicket(ticketId, ticketUpdateRequestDTO);
    }

    public Long deleteTicket(Long ticketId){
        return eventSubService.deleteTicket(ticketId);
    }

    public List<EventBasicFindAllResponseDTO> findEventsByHostId(Long hostId){
        return Optional.ofNullable(eventBasicRepository.selectEventsByHostId(hostId))
                .filter(events -> !events.isEmpty())
                .orElseThrow(() -> EventNotFoundException.EXCEPTION)
                .stream()
                .map(EventBasicFindAllResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public Long updateEvent(Long id, EventUpdateRequestDTO eventUpdateRequestDTO){
        return eventSubService.updateEvent(id, eventUpdateRequestDTO);
    }

    public Long deleteEvent(Long id){
        return eventSubService.deleteEvent(id);
    }

    @Transactional(readOnly = true)
    public List<EventBasicFindAllResponseDTO> findEventsByCategory(Category category){
        return Optional.ofNullable(eventBasicRepository.selectEventsByCategory(category.getId()))
                .filter(events -> !events.isEmpty())
                .orElseThrow(() -> EventNotFoundException.EXCEPTION)
                .stream()
                .map(EventBasicFindAllResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

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
