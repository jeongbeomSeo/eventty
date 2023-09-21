package com.eventty.businessservice.event.application.service.subservices;

import com.eventty.businessservice.event.application.dto.request.EventCreateRequestDTO;
import com.eventty.businessservice.event.application.dto.request.TicketUpdateRequestDTO;
import com.eventty.businessservice.event.application.dto.response.TicketResponseDTO;
import com.eventty.businessservice.event.domain.entity.EventImageEntity;
import com.eventty.businessservice.event.domain.entity.TicketEntity;
import com.eventty.businessservice.event.domain.exception.EventImageNotFoundException;
import com.eventty.businessservice.event.domain.exception.TicketNotFoundException;
import com.eventty.businessservice.event.domain.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TicketService {

    private final TicketRepository ticketRepository;

    public List<TicketResponseDTO> findTicketsByEventId(Long eventId){
        return Optional.ofNullable(ticketRepository.selectTicketByEventId(eventId))
                .map(tickets -> tickets.stream()
                        .map(TicketResponseDTO::fromEntity)
                        .toList())
                .orElseThrow(()-> TicketNotFoundException.EXCEPTION);
    }

    public List<TicketResponseDTO> findTicketsByTicketIdList(List<Long> ticketIds){
        List<TicketEntity> ticketList = getTicketListIfExists(ticketIds);
        return ticketList.stream()
                .map(TicketResponseDTO::fromEntity)
                .toList();
    }

    public Long findEventIdByTicketId(Long ticketId){
        TicketEntity ticket = getTicketIfExists(ticketId);
        return ticketId;
    }

    public Long createTickets(Long eventId, EventCreateRequestDTO eventCreateRequest){
        eventCreateRequest.getTickets().stream()
                .map(ticketCreateRequest -> ticketCreateRequest.toEntity(eventId))
                .forEach(ticketRepository::insertTicket);

        return eventId;
    }

    public Long deleteTickets(Long eventId){
        // 삭제 전, 데이터 존재 확인
        List<TicketEntity> tickets = getTicketListIfExists(eventId);

        ticketRepository.deleteTicketsByEventId(eventId);
        return eventId;
    }

    public Long updateTicket(Long ticketId, TicketUpdateRequestDTO ticketUpdateRequestDTO) {
        // 업데이트 전, 데이터 존재 확인
        TicketEntity ticket = getTicketIfExists(ticketId);

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

    public TicketEntity deleteTicket(Long ticketId) {
        // 삭제 전, 데이터 존재 확인
        TicketEntity ticket = getTicketIfExists(ticketId);

        ticketRepository.deleteTicketById(ticketId);
        return ticket;
    }

    private List<TicketEntity> getTicketListIfExists(Long eventId) {
        Optional<List<TicketEntity>> ticketOptional = Optional.ofNullable(ticketRepository.selectTicketByEventId(eventId));
        return ticketOptional.orElseThrow(() -> TicketNotFoundException.EXCEPTION);
    }

    private TicketEntity getTicketIfExists(Long ticketId) {
        Optional<TicketEntity> ticketOptional = Optional.ofNullable(ticketRepository.selectTicketById(ticketId));
        return ticketOptional.orElseThrow(() -> TicketNotFoundException.EXCEPTION);
    }

    private List<TicketEntity> getTicketListIfExists(List<Long> ticketIdList) {
        List<TicketEntity> ticketList = new ArrayList<>();
        for (Long ticketId : ticketIdList) {
            Optional<TicketEntity> ticketOptional = Optional.ofNullable(getTicketIfExists(ticketId));
            ticketOptional.ifPresent(ticketList::add); // 데이터가 존재하면 리스트에 추가
        }
        return ticketList;
    }
}
