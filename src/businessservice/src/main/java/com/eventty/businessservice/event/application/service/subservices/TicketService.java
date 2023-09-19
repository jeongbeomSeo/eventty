package com.eventty.businessservice.event.application.service.subservices;

import com.eventty.businessservice.event.application.dto.request.EventCreateRequestDTO;
import com.eventty.businessservice.event.application.dto.request.TicketUpdateRequestDTO;
import com.eventty.businessservice.event.application.dto.response.TicketResponseDTO;
import com.eventty.businessservice.event.domain.entity.TicketEntity;
import com.eventty.businessservice.event.domain.exception.TicketNotFoundException;
import com.eventty.businessservice.event.domain.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class TicketService {
    private final TicketRepository ticketRepository;
    private final EventBasicService eventBasicService;

    public List<TicketResponseDTO> findTicketsByEventId(Long eventId){
        return Optional.ofNullable(ticketRepository.selectTicketByEventId(eventId))
                .map(tickets -> tickets.stream()
                        .map(TicketResponseDTO::fromEntity)
                        .toList())
                .orElseThrow(()-> TicketNotFoundException.EXCEPTION);
    }

    public Long createTickets(Long eventId, EventCreateRequestDTO eventCreateRequest){
        eventCreateRequest.getTickets().stream()
                .map(ticketCreateRequest -> ticketCreateRequest.toEntity(eventId))
                .forEach(ticketRepository::insertTicket);

        return eventId;
    }

    public Long deleteTickets(Long eventId){
        // 삭제 전, 데이터 존재 확인
        List<TicketEntity> tickets = ticketRepository.selectTicketByEventId(eventId);
        if(tickets == null) {
            throw TicketNotFoundException.EXCEPTION;
        }

        ticketRepository.deleteTicketsByEventId(eventId);
        return eventId;
    }

    public Long updateTicket(Long ticketId, TicketUpdateRequestDTO ticketUpdateRequestDTO) {
        // 업데이트 전, 데이터 존재 확인
        TicketEntity ticket = ticketRepository.selectTicketById(ticketId);
        if(ticket == null) {
            throw TicketNotFoundException.EXCEPTION;
        }

        // 티켓 이름 수정이 들어왔을 경우에만 업데이트
        if(ticketUpdateRequestDTO.getName() != null) {
            ticket.updateName(ticketUpdateRequestDTO.getName());
        }

        // 티켓 가격 수정이 들어왔을 경우에만 업데이트
        if(ticketUpdateRequestDTO.getPrice() != null) {
            ticket.updatePrice(ticketUpdateRequestDTO.getPrice());
        }

        ticketRepository.updateTicket(ticket);
        return ticketId;
    }

    public Long deleteTicket(Long ticketId) {
        // 삭제 전, 데이터 존재 확인
        TicketEntity ticket = ticketRepository.selectTicketById(ticketId);
        if(ticket == null) {
            throw TicketNotFoundException.EXCEPTION;
        }

        // 삭제된 티켓 수 만큼 행사 인원 수 감소
        eventBasicService.subtractParticipateNum(ticket.getEventId(), ticket.getQuantity());

        // 티켓 삭제
        ticketRepository.deleteTicketById(ticketId);
        return ticketId;
    }
}
